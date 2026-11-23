package jp.m.multi.dao.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.m.multi.dto.auth.AccountAuthDto;
import jp.m.multi.dto.auth.AccountStatusDto;

/** Serviceから借りたConnectionは、DAOでは閉じません。 */
public class JdbcAccountDao implements AccountDao {
    private static final String COLUMNS =
            "a.account_id, a.account_type, a.is_active AS account_active, "
            + "a.profile_image_id, p.image_path AS profile_image_path, "
            + "CASE a.account_type WHEN 'STUDENT' THEN s.student_id "
            + "WHEN 'STAFF' THEN t.staff_id END AS profile_id, "
            + "CASE a.account_type WHEN 'STUDENT' THEN s.is_active "
            + "WHEN 'STAFF' THEN t.is_active END AS profile_active, "
            + "CASE a.account_type WHEN 'STUDENT' THEN CONCAT(s.family_name, ' ', s.given_name) "
            + "WHEN 'STAFF' THEN CONCAT(t.family_name, ' ', t.given_name) END AS display_name ";
    private static final String FROM =
            "FROM account a "
            + "JOIN profile_image p ON p.profile_image_id = a.profile_image_id "
            + "LEFT JOIN student s ON s.account_id = a.account_id AND a.account_type = 'STUDENT' "
            + "LEFT JOIN staff t ON t.account_id = a.account_id AND a.account_type = 'STAFF' ";

    @Override
    public AccountAuthDto findBySchoolEmail(Connection connection, String email) throws SQLException {
        String sql = "SELECT a.password_hash, " + COLUMNS + FROM + "WHERE a.school_email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    AccountStatusDto status = map(result);
                    String hash = result.getString("password_hash");
                    return new AccountAuthDto(status, hash);
                }
            }
        }
        return null;
    }

    @Override
    public AccountStatusDto findStatusById(Connection connection, long accountId) throws SQLException {
        String sql = "SELECT " + COLUMNS + FROM + "WHERE a.account_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, accountId);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return map(result);
                }
            }
        }
        return null;
    }

    private AccountStatusDto map(ResultSet result) throws SQLException {
        long id = result.getLong("profile_id");
        Long profileId = Long.valueOf(id);
        if (result.wasNull()) {
            profileId = null;
        }
        return new AccountStatusDto(result.getLong("account_id"), result.getString("account_type"),
                result.getBoolean("account_active"), profileId,
                result.getBoolean("profile_active"), result.getString("display_name"),
                result.getLong("profile_image_id"), result.getString("profile_image_path"));
    }
}