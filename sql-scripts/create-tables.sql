USE university_system;

DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS fees;
DROP TABLE IF EXISTS attendances;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS users;



CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'INSTRUCTOR', 'STUDENT') NOT NULL,
    first_name VARCHAR(20), 
    last_name VARCHAR(20),  
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(15) NOT NULL UNIQUE,
    title VARCHAR(50) NOT NULL,
    credits INT NOT NULL,
    instructor_id INT,
    description VARCHAR(50),
    
    CONSTRAINT fk_course_instructor 
        FOREIGN KEY (instructor_id) REFERENCES users(id)
        ON DELETE SET NULL -- so the course remains even if teacher is deleted
);

CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    semester VARCHAR(20) NOT NULL,
    grade VARCHAR(5),
    status ENUM('ENROLLED', 'DROPPED', 'COMPLETED') DEFAULT 'ENROLLED',
    enrollment_date DATE DEFAULT (CURRENT_DATE),

    CONSTRAINT fk_enrollment_student 
        FOREIGN KEY (student_id) REFERENCES users(id)
        ON DELETE CASCADE,
        
    CONSTRAINT fk_enrollment_course 
        FOREIGN KEY (course_id) REFERENCES courses(id)
        ON DELETE CASCADE,

    CONSTRAINT unique_enrollment
        UNIQUE (student_id, course_id, semester)
);



CREATE TABLE attendances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    session_date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'EXCUSED') NOT NULL,
    remarks VARCHAR(30),

    CONSTRAINT fk_attendance_enrollment 
        FOREIGN KEY (enrollment_id) REFERENCES enrollments(id)
        ON DELETE CASCADE
);


CREATE TABLE fees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    amount INT NOT NULL,
    type ENUM('TUITION', 'LAB', 'LIBRARY', 'OTHER') NOT NULL,
    is_paid BOOLEAN DEFAULT FALSE,
    due_date DATE NOT NULL,
    payment_date DATE,

    CONSTRAINT fk_fee_student 
        FOREIGN KEY (student_id) REFERENCES users(id)
        ON DELETE CASCADE
);










