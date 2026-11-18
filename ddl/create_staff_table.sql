-- 教職員
CREATE TABLE
    `staff` (
        `staff_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '教職員ID',
        `account_id` BIGINT UNSIGNED NOT NULL COMMENT 'アカウントID',
        `account_type` VARCHAR(32) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL DEFAULT 'STAFF' COMMENT 'アカウント区分整合値。STAFF',
            `family_name` VARCHAR(100) NOT NULL COMMENT '姓',
            `given_name` VARCHAR(100) NOT NULL COMMENT '名',
            `family_name_kana` VARCHAR(100) NOT NULL COMMENT '姓ふりがな',
            `given_name_kana` VARCHAR(100) NOT NULL COMMENT '名ふりがな',
            `birth_date` DATE NULL COMMENT '生年月日',
            `gender` VARCHAR(20) NULL COMMENT '性別',
            `phone` VARCHAR(30) NULL COMMENT '電話番号',
            `contact_email` VARCHAR(254) NULL COMMENT '連絡用メールアドレス',
            `employment_type` VARCHAR(32) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL COMMENT '雇用所属区分。FULL_TIME / PART_TIME',
            `employment_status` VARCHAR(32) CHARACTER
        SET
            ascii COLLATE ascii_bin NOT NULL COMMENT '在籍ステータス。EMPLOYED / ON_LEAVE / RETIRED',
            `status_since` DATE NOT NULL COMMENT 'ステータス開始日',
            `employment_start_date` DATE NOT NULL COMMENT '在籍開始日',
            `employment_end_date` DATE NULL COMMENT '在籍終了日',
            `is_active` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '教職員有効フラグ',
            `created_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時。Asia/Tokyo',
            `updated_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新日時。Asia/Tokyo。updated_at は ON UPDATE CURRENT_TIMESTAMP(6)',
            `row_version` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '更新版番号。更新時にアプリケーションが比較して1加算',
            PRIMARY KEY (`staff_id`),
            UNIQUE KEY `uq_staff_1` (`account_id`),
            KEY `ix_staff_2` (`account_id`, `account_type`),
            CONSTRAINT `ck_staff_1` CHECK (account_type IN ('STAFF')),
            CONSTRAINT `ck_staff_2` CHECK (employment_type IN ('FULL_TIME', 'PART_TIME')),
            CONSTRAINT `ck_staff_3` CHECK (
                employment_status IN ('EMPLOYED', 'ON_LEAVE', 'RETIRED')
            ),
            CONSTRAINT `ck_staff_4` CHECK (
                employment_end_date IS NULL
                OR employment_start_date <= employment_end_date
            ),
            CONSTRAINT `ck_staff_5` CHECK (is_active IN (0, 1)),
            CONSTRAINT `fk_staff_1` FOREIGN KEY (`account_id`, `account_type`) REFERENCES `account` (`account_id`, `account_type`) ON DELETE RESTRICT ON UPDATE RESTRICT
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '教職員';