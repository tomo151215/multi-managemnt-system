package jp.m.multi.dto.auth;

public enum AccountType {
    STAFF("教職員"),
    STUDENT("生徒");

    private final String label;

    AccountType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
