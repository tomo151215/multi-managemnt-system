-- プロフィール画像マスタ
CREATE TABLE
    `profile_image` (
        `profile_image_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'プロフィール画像ID',
        `image_code` VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '画像識別コード',
        `image_path` VARCHAR(500) NOT NULL COMMENT '画像ファイルパスまたは保存キー',
        `is_active` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '有効フラグ',
        `created_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '作成日時。Asia/Tokyo',
        `updated_at` DATETIME (6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新日時。Asia/Tokyo。updated_at は ON UPDATE CURRENT_TIMESTAMP(6)',
        PRIMARY KEY (`profile_image_id`),
        UNIQUE KEY `uq_profile_image_1` (`image_code`),
        CONSTRAINT `ck_profile_image_1` CHECK (is_active IN (0, 1))
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'プロフィール画像';