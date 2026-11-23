package jp.m.multi.dto.auth;

public class AccountStatusDto {
    private final long accountId;
    private final String accountType;
    private final boolean accountActive;
    private final Long profileId;
    private final boolean profileActive;
    private final String displayName;
    private final long profileImageId;
    private final String profileImagePath;

    public AccountStatusDto(long accountId, String accountType, boolean accountActive,
            Long profileId, boolean profileActive, String displayName, long profileImageId, String profileImagePath) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountActive = accountActive;
        this.profileId = profileId;
        this.profileActive = profileActive;
        this.displayName = displayName;
        this.profileImageId = profileImageId;
        this.profileImagePath = profileImagePath;
    }

    /** 利用可能ならログイン情報を返し、利用不可ならnull */
    public LoginUserDto toActiveUser() {
        if (!accountActive || !profileActive || profileId == null) {
            return null;
        }
        AccountType type;
        if ("STUDENT".equals(accountType)) {
            type = AccountType.STUDENT;
        } else if ("STAFF".equals(accountType)) {
            type = AccountType.STAFF;
        } else {
            return null;
        }
        return new LoginUserDto(accountId, profileId, type, displayName, profileImageId, profileImagePath);
    }
}