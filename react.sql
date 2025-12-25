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
('Spring Boot Cơ Bản', 'Xây dựng REST API với Spring Boot', 799000, 2),
('Spring Security', 'Bảo mật ứng dụng với Spring Security', 899000, 2),
('React Cơ Bản', 'Học React từ cơ bản đến thực hành', 599000, 3),
('React Nâng Cao', 'Hook, Performance và Best Practices', 799000, 3),
('Vue 3 + TypeScript', 'Xây dựng SPA với Vue 3 và TS', 699000, 4),
('Docker Cho Người Mới', 'Docker từ cơ bản đến triển khai', 649000, 5),
('DevOps Căn Bản', 'CI/CD với Docker và GitHub Actions', 899000, 5),
('Kotlin Backend', 'Xây dựng backend với Kotlin Spring', 799000, 2),
('System Design Cơ Bản', 'Thiết kế hệ thống cho developer', 999000, 6);

ALTER TABLE react.users
MODIFY role ENUM('STUDENT', 'INSTRUCTOR', 'ADMIN');
UPDATE `react`.`users` SET `role` = 'ADMIN' WHERE (`id` = '3');
