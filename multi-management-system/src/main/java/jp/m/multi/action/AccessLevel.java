package jp.m.multi.action;

import java.util.HashMap;
import java.util.Map;

public enum AccessLevel {
    PUBLIC(0),
    LOGIN_REQUIRED(1),
    STAFF_ONLY(2),
    STUDENT_ONLY(3);

    private static final Map<Integer, AccessLevel> LEVEL_MAP = new HashMap<>();

    static {
        for (AccessLevel level : values()) {
            LEVEL_MAP.put(level.level, level);
        }
    }
    private final int level;

    AccessLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static AccessLevel fromLevel(int level) {
        AccessLevel target = LEVEL_MAP.get(level);
        if (target == null) {
            throw new IllegalArgumentException("Unknown access level: " + level);
        }
        return target;
    }
}
