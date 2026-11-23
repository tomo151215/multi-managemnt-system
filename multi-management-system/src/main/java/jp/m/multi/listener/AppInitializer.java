package jp.m.multi.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.SessionCookieConfig;
import jp.m.multi.config.AppConfig;
import jp.m.multi.config.AppContext;
import jp.m.multi.config.DataSourceFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jp.m.multi.security.BcryptPasswordHasher;
import jp.m.multi.dao.auth.JdbcAccountDao;
import jp.m.multi.service.auth.AuthService;

public final class AppInitializer implements ServletContextListener {
    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        AppConfig config = AppConfig.load();
        SessionCookieConfig cookie = event.getServletContext().getSessionCookieConfig();
        cookie.setHttpOnly(true);
        cookie.setSecure(config.isSecureCookie());
        cookie.setAttribute("SameSite", "Lax");
        dataSource = DataSourceFactory.create(config);
        AuthService auth = new AuthService(dataSource, new JdbcAccountDao(), new BcryptPasswordHasher());
        event.getServletContext().setAttribute(AppContext.KEY, new AppContext(auth, config.getSessionTimeout()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            // アプリが管理しているDB接続プールを閉じる
            if (dataSource != null) {
                dataSource.close();
            }
        } finally {
            // MySQLドライバーの後処理用スレッドを停止する
            AbandonedConnectionCleanupThread.checkedShutdown();

            // アプリ全体で共有していた情報を取り除く
            event.getServletContext().removeAttribute(AppContext.KEY);
        }
    }
}
