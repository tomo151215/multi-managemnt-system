package jp.m.multi.service.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;
import javax.sql.DataSource;
import jp.m.multi.dao.auth.AccountDao;
import jp.m.multi.dto.auth.AccountAuthDto;
import jp.m.multi.dto.auth.AccountStatusDto;
import jp.m.multi.dto.auth.LoginUserDto;
import jp.m.multi.security.PasswordHasher;
import jp.m.multi.security.BcryptPasswordHasher;

public class AuthService {
    private final DataSource dataSource;
    private final AccountDao dao;
    private final PasswordHasher hasher;
    private final String dummyHash;

    public AuthService(DataSource dataSource, AccountDao dao, PasswordHasher hasher) {
        this.dataSource = dataSource;
        this.dao = dao;
        this.hasher = hasher;
        this.dummyHash = hasher.hash(UUID.randomUUID().toString().toCharArray());
    }

    /** 認証成功なら利用者情報、認証失敗ならnullを返す。DB障害は例外 */
    public LoginUserDto authenticate(String email, char[] password) throws SQLException {
        if (email == null || email.isBlank() || email.length() > 254) {
            return null;
        }
        if (!BcryptPasswordHasher.isValidPassword(password)) {
            return null;
        }
        String normalizedEmail = email.strip().toLowerCase(Locale.ROOT);
        AccountAuthDto account;
        try (Connection connection = dataSource.getConnection()) {
            account = dao.findBySchoolEmail(connection, normalizedEmail);
        }
        // 重い照合の前にDB接続を返却します。未知のメールでもダミーで照合します。
        String hash = dummyHash;
        if (account != null) {
            hash = account.getPasswordHash();
        }
        boolean matches = hasher.verify(password, hash);
        if (!matches || account == null) {
            return null;
        }
        return account.getStatus().toActiveUser();
    }

    public LoginUserDto currentUser(long accountId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            AccountStatusDto status = dao.findStatusById(connection, accountId);
            if (status == null) {
                return null;
            }
            return status.toActiveUser();
        }
    }
}
