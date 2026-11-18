CREATE TABLE
    `account` (
        `account_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'アカウントID',
        `school_email` VARCHAR(254) CHARACTER
        SET
            ascii COLLATE ascii_general_ci NOT NULL COMMENT '学校配布メールアドレス',
            `password_hash` VARCHAR(255) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL COMMENT 'パスワードハッシュ',
            `account_type` VARCHAR(32) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL COMMENT '利用者区分。STUDENT / STAFF',
            `is_active` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT 'アカウント有効フラグ',
            `created_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時。Asia/Tokyo',
            `updated_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新日時。Asia/Tokyo。updated_at は ON UPDATE CURRENT_TIMESTAMP(6)',
            `row_version` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新版番号。更新時にアプリケーションが比較して1加算',
            PRIMARY KEY (`account_id`),
            UNIQUE KEY `uq_account_1` (`school_email`),
            UNIQUE KEY `uq_account_2` (`account_id`, `account_type`),
            CONSTRAINT `ck_account_1` CHECK (account_type IN ('STUDENT', 'STAFF')),
            CONSTRAINT `ck_account_2` CHECK (is_active IN (0, 1))
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'アカウント';