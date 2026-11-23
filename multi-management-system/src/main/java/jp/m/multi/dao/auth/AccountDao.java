package jp.m.multi.dao.auth;

import java.sql.Connection;
import java.sql.SQLException;

import jp.m.multi.dto.auth.AccountAuthDto;
import jp.m.multi.dto.auth.AccountStatusDto;

public interface AccountDao {
    AccountAuthDto findBySchoolEmail(Connection connection, String email) throws SQLException;
    AccountStatusDto findStatusById(Connection connection, long accountId) throws SQLException;
}
