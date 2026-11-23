package jp.m.multi.dto.auth;

/** 認証時だけ使用 */
public class AccountAuthDto {
    private final AccountStatusDto status;
    private final String passwordHash;

    public AccountAuthDto(AccountStatusDto status, String passwordHash) {
        this.status = status;
        this.passwordHash = passwordHash;
    }

    public AccountStatusDto getStatus() {
        return status;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return "AccountAuthDto[credentials=REDACTED]";
    }
}