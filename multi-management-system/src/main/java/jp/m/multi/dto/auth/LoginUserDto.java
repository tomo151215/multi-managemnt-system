package jp.m.multi.dto.auth;

import java.io.Serializable;

public final class LoginUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long accountId;
    private final int profileId;
    private final AccountType accountType;
    private final String displayName;
    private final long profileImageId;
    private final String prifileImagePath;

    public LoginUserDto(long accountId, int profileId, AccountType accountType, String displayName, long profileImageId,
            String prifileImagePath) {
        this.accountId = accountId;
        this.profileId = profileId;
        this.accountType = accountType;
        this.displayName = displayName;
        this.profileImageId = profileImageId;
        this.prifileImagePath = prifileImagePath;
    }

    public long getAccountId() {
        return accountId;
    }

    public int getProfileId() {
        return profileId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getProfileImageId() {
        return profileImageId;
    }

    public String getPrifileImagePath() {
        return prifileImagePath;
    }

}
