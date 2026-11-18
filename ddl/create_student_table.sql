CREATE TABLE
    `student` (
        `student_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '生徒ID',
        `account_id` BIGINT UNSIGNED NOT NULL COMMENT 'アカウントID',
        `account_type` VARCHAR(32) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL DEFAULT 'STUDENT' COMMENT 'アカウント区分整合値。STUDENT',
            `student_number` VARCHAR(30) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL COMMENT '学籍番号',
            `family_name` VARCHAR(100) NOT NULL COMMENT '姓',
            `given_name` VARCHAR(100) NOT NULL COMMENT '名',
            `family_name_kana` VARCHAR(100) NOT NULL COMMENT '姓ふりがな',
            `given_name_kana` VARCHAR(100) NOT NULL COMMENT '名ふりがな',
            `birth_date` DATE NULL COMMENT '生年月日',
            `gender` VARCHAR(20) NULL COMMENT '性別',
            `phone` VARCHAR(30) NULL COMMENT '電話番号',
            `contact_email` VARCHAR(254) NULL COMMENT '連絡用メールアドレス',
            `postal_code` VARCHAR(12) NULL COMMENT '郵便番号',
            `address` VARCHAR(500) NULL COMMENT '住所',
            `address_kana` VARCHAR(500) NULL COMMENT '住所ふりがな',
            `photo_key` VARCHAR(500) NULL COMMENT '顔写真保存キー',
            `is_active` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '生徒有効フラグ',
            `created_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時。Asia/Tokyo',
            `updated_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新日時。Asia/Tokyo。updated_at は ON UPDATE CURRENT_TIMESTAMP(6)',
            `row_version` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新版番号。更新時にアプリケーションが比較して1加算',
            PRIMARY KEY (`student_id`),
            UNIQUE KEY `uq_student_1` (`account_id`),
            UNIQUE KEY `uq_student_2` (`student_number`),
            KEY `ix_student_3` (`account_id`, `account_type`),
            CONSTRAINT `ck_student_1` CHECK (account_type IN ('STUDENT')),
            CONSTRAINT `ck_student_2` CHECK (is_active IN (0, 1)),
            CONSTRAINT `fk_student_1` FOREIGN KEY (`account_id`, `account_type`) REFERENCES `account` (`account_id`, `account_type`) ON DELETE RESTRICT ON UPDATE RESTRICT
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生徒';