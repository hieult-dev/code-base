CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    role ENUM('STUDENT', 'INSTRUCTOR') DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) DEFAULT 0,
    instructor_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_course_instructor
        FOREIGN KEY (instructor_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);
CREATE TABLE lessons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    video_url VARCHAR(255),
    lesson_order INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lesson_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE
);
CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    course_id INT NOT NULL,
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_enroll_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_enroll_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE,

    UNIQUE (user_id, course_id)
);
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    course_id INT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_review_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE
);

INSERT INTO users (full_name, email, role) VALUES
('Nguyễn Văn A', 'a@gmail.com', 'INSTRUCTOR'),
('Trần Văn B', 'b@gmail.com', 'STUDENT');
INSERT INTO courses (title, description, price, instructor_id)
VALUES
('MySQL Cơ Bản', 'Học MySQL từ zero', 499000, 1);
INSERT INTO lessons (course_id, title, lesson_order)
VALUES
(1, 'Giới thiệu MySQL', 1),
(1, 'Tạo database & table', 2);
INSERT INTO enrollments (user_id, course_id)
VALUES (2, 1);
INSERT INTO reviews (user_id, course_id, rating, comment)
VALUES (2, 1, 5, 'Khóa học rất dễ hiểu');
INSERT INTO courses (title, description, price, instructor_id)
VALUES
('MySQL Nâng Cao', 'Tối ưu truy vấn và index MySQL', 699000, 1),
('Spring Boot Cơ Bản', 'Xây dựng REST API với Spring Boot', 799000, 1),
('Spring Security', 'Bảo mật ứng dụng với Spring Security', 899000, 2),
('React Cơ Bản', 'Học React từ cơ bản đến thực hành', 599000, 1),
('React Nâng Cao', 'Hook, Performance và Best Practices', 799000, 1),
('Vue 3 + TypeScript', 'Xây dựng SPA với Vue 3 và TS', 699000, 1),
('Docker Cho Người Mới', 'Docker từ cơ bản đến triển khai', 649000, 1),
('DevOps Căn Bản', 'CI/CD với Docker và GitHub Actions', 899000, 1),
('Kotlin Backend', 'Xây dựng backend với Kotlin Spring', 799000, 1),
('System Design Cơ Bản', 'Thiết kế hệ thống cho developer', 999000, 1);

ALTER TABLE react.users
MODIFY role ENUM('STUDENT', 'INSTRUCTOR', 'ADMIN');
UPDATE `react`.`users` SET `role` = 'ADMIN' WHERE (`id` = '3');


UPDATE react.courses
SET active = TRUE;
UPDATE react.courses
SET active = FALSE
WHERE id IN (12, 14, 18);

CREATE TABLE chat_sessions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  session_key VARCHAR(64) NOT NULL UNIQUE,
  user_id INT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_chat_sessions_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE SET NULL,
  INDEX idx_chat_sessions_user (user_id),
  INDEX idx_chat_sessions_updated (updated_at)
) ENGINE=InnoDB;

-- 2) Messages
CREATE TABLE chat_messages (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  session_id BIGINT NOT NULL,
  role ENUM('user','assistant') NOT NULL,
  content MEDIUMTEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_chat_messages_session
    FOREIGN KEY (session_id) REFERENCES chat_sessions(id)
    ON DELETE CASCADE,
  INDEX idx_chat_messages_session_time (session_id, created_at),
  INDEX idx_chat_messages_role (role)
) ENGINE=InnoDB;
ALTER TABLE chat_messages MODIFY role VARCHAR(16) NOT NULL;
UPDATE chat_messages
SET role = UPPER(role)
WHERE role IN ('user', 'assistant');