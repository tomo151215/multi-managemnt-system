
START TRANSACTION;
INSERT INTO account (school_email, password_hash, account_type, profile_image_id)
VALUES ('student.demo@student.multi-m.ac.jp', '$2a$12$785FfgI0OEeS3UveTo3C/O18D.yD2o7hTrB.oi3M9k4JRGZkK0VG.', 'STUDENT',
    (SELECT profile_image_id FROM profile_image WHERE is_active = 1 ORDER BY RAND() LIMIT 1));
SET @new_account_id = LAST_INSERT_ID();
INSERT INTO student (account_id, student_number, family_name, given_name, family_name_kana, given_name_kana)
VALUES (@new_account_id, 'DEMO001', '生徒', 'デモ', 'セイト', 'デモ');
INSERT INTO account (school_email, password_hash, account_type, profile_image_id)
VALUES ('staff.demo@teacher.multi-m.ac.jp', '$2a$12$0whu6U0T/vs2X8KYtu83QuP5RKRwqVqVYJAJzMENwpDbhGA/VCqC6', 'STAFF',
    (SELECT profile_image_id FROM profile_image WHERE is_active = 1 ORDER BY RAND() LIMIT 1));
SET @new_account_id = LAST_INSERT_ID();
INSERT INTO staff (account_id, family_name, given_name, family_name_kana, given_name_kana, employment_type, employment_status, status_since, employment_start_date)
VALUES (@new_account_id, '教職員', 'デモ', 'キョウショクイン', 'デモ', 'FULL_TIME', 'EMPLOYED', CURRENT_DATE, CURRENT_DATE);
COMMIT;

-- 生徒デモアカウント： student.demo@student.multi-m.ac.jp, test@student
-- 教職員デモアカウント： staff.demo@teacher.multi-m.ac.jp, test@staff