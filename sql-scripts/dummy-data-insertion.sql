USE university_system;

-- Optional: clear existing data in correct FK order
SET FOREIGN_KEY_CHECKS = 0;
DELETE
FROM fees;
DELETE
FROM attendances;
DELETE
FROM enrollments;
DELETE
FROM courses;
DELETE
FROM users;

-- Reset auto-increment so IDs start again from 1 (so your hardcoded IDs work)
ALTER TABLE users
    AUTO_INCREMENT = 1;
ALTER TABLE courses
    AUTO_INCREMENT = 1;
ALTER TABLE enrollments
    AUTO_INCREMENT = 1;
ALTER TABLE attendances
    AUTO_INCREMENT = 1;
ALTER TABLE fees
    AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;


-- ===========================
-- USERS
-- ===========================
INSERT INTO users (email, password_hash, role, first_name, last_name, is_active)
VALUES ('admin@uni.edu', 'admin123', 'ADMIN', 'System', 'Admin', TRUE),

       -- Instructors (10)
       ('prakash.sharma@uni.edu', 'instr123', 'INSTRUCTOR', 'Prakash', 'Sharma', TRUE),
       ('sita.koirala@uni.edu', 'instr123', 'INSTRUCTOR', 'Sita', 'Koirala', TRUE),
       ('ramesh.adhikari@uni.edu', 'instr123', 'INSTRUCTOR', 'Ramesh', 'Adhikari', TRUE),
       ('gita.shrestha@uni.edu', 'instr123', 'INSTRUCTOR', 'Gita', 'Shrestha', TRUE),
       ('bikash.thapa@uni.edu', 'instr123', 'INSTRUCTOR', 'Bikash', 'Thapa', TRUE),
       ('anil.gurung@uni.edu', 'instr123', 'INSTRUCTOR', 'Anil', 'Gurung', TRUE),
       ('saraswati.rana@uni.edu', 'instr123', 'INSTRUCTOR', 'Saraswati', 'Rana', TRUE),
       ('dipak.khadka@uni.edu', 'instr123', 'INSTRUCTOR', 'Dipak', 'Khadka', TRUE),
       ('nirmala.poudel@uni.edu', 'instr123', 'INSTRUCTOR', 'Nirmala', 'Poudel', TRUE),
       ('hari.basnet@uni.edu', 'instr123', 'INSTRUCTOR', 'Hari', 'Basnet', TRUE),

       -- Students (20)
       ('aayush.shrestha@uni.edu', 'stud123', 'STUDENT', 'Aayush', 'Shrestha', TRUE),
       ('anusha.adhikari@uni.edu', 'stud123', 'STUDENT', 'Anusha', 'Adhikari', TRUE),
       ('suman.thapa@uni.edu', 'stud123', 'STUDENT', 'Suman', 'Thapa', TRUE),
       ('rojina.karki@uni.edu', 'stud123', 'STUDENT', 'Rojina', 'Karki', TRUE),
       ('prabin.gurung@uni.edu', 'stud123', 'STUDENT', 'Prabin', 'Gurung', TRUE),
       ('kritika.rana@uni.edu', 'stud123', 'STUDENT', 'Kritika', 'Rana', TRUE),
       ('sagar.khadka@uni.edu', 'stud123', 'STUDENT', 'Sagar', 'Khadka', TRUE),
       ('nisha.poudel@uni.edu', 'stud123', 'STUDENT', 'Nisha', 'Poudel', TRUE),
       ('roshan.basnet@uni.edu', 'stud123', 'STUDENT', 'Roshan', 'Basnet', TRUE),
       ('sabina.tamang@uni.edu', 'stud123', 'STUDENT', 'Sabina', 'Tamang', TRUE),
       ('dipesh.bista@uni.edu', 'stud123', 'STUDENT', 'Dipesh', 'Bista', TRUE),
       ('anjali.rijal@uni.edu', 'stud123', 'STUDENT', 'Anjali', 'Rijal', TRUE),
       ('bibek.dahal@uni.edu', 'stud123', 'STUDENT', 'Bibek', 'Dahal', TRUE),
       ('pooja.joshi@uni.edu', 'stud123', 'STUDENT', 'Pooja', 'Joshi', TRUE),
       ('ritesh.pandey@uni.edu', 'stud123', 'STUDENT', 'Ritesh', 'Pandey', TRUE),
       ('shreeya.kc@uni.edu', 'stud123', 'STUDENT', 'Shreeya', 'KC', TRUE),
       ('kiran.rai@uni.edu', 'stud123', 'STUDENT', 'Kiran', 'Rai', TRUE),
       ('manisha.limbu@uni.edu', 'stud123', 'STUDENT', 'Manisha', 'Limbu', TRUE),
       ('suraj.magar@uni.edu', 'stud123', 'STUDENT', 'Suraj', 'Magar', TRUE),
       ('priyanka.shahi@uni.edu', 'stud123', 'STUDENT', 'Priyanka', 'Shahi', TRUE);

-- IDs now are:
-- 1 = admin
-- 2..11 = instructors
-- 12..31 = students

-- ===========================
-- COURSES
-- ===========================
INSERT INTO courses (code, title, credits, instructor_id, description)
VALUES ('CS101', 'Computer Bigyan Parichaya', 3, 2, 'Computing ko aadhar dharana ra parichaya'),
       ('CS201', 'Data Structures', 4, 2, 'Core data structures course'),
       ('CS301', 'Algorithms', 4, 8, 'Algorithm design and analysis'),
       ('MATH101', 'Calculus I', 4, 3, 'Limit, derivative ra applications'),
       ('MATH201', 'Linear Algebra', 4, 4, 'Matrix, vector ra eigenvalues'),
       ('ENG101', 'English Composition', 3, NULL, 'Academic writing skills'),
       ('ENG201', 'Technical Writing', 3, 7, 'Report ra technical document lekhan'),
       ('MGMT101', 'Management Parichaya', 3, 6, 'Planning, organizing ra leadership basics'),
       ('PHY101', 'Physics I', 4, NULL, 'Mechanics ra waves ko parichaya'),
       ('STAT101', 'Statistics Basics', 3, 9, 'Data, probability ra inference basics');

-- IDs now are:
-- 1 = CS101, 2 = CS201, 3 = CS301, 4 = MATH101, 5 = MATH201, 6 = ENG101, 7 = ENG201, 8 = MGMT101, 9 = PHY101, 10 = STAT101

-- ===========================
-- ENROLLMENTS
-- (student_id, course_id, semester must be unique)
-- ===========================
INSERT INTO enrollments (student_id, course_id, semester, grade, status, enrollment_date)
VALUES (12, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-20'), -- Aayush in CS101
       (12, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-20'), -- Aayush in MATH101

       (13, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-20'), -- Anusha in CS101
       (13, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-20'), -- Anusha in MATH101

       (14, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-21'), -- Suman in CS101
       (14, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-21'), -- Suman in MATH101

       (15, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-21'), -- Rojina in CS101
       (15, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-21'), -- Rojina in MATH101

       (16, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-22'), -- Prabin in CS101
       (16, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-22'), -- Prabin in MATH101

       (17, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-22'), -- Kritika in CS101
       (17, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-22'), -- Kritika in MATH101

       (18, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-23'), -- Sagar in CS101
       (18, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-23'), -- Sagar in MATH101

       (19, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-23'), -- Nisha in CS101
       (19, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-23'), -- Nisha in MATH101

       (20, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-24'), -- Roshan in CS101
       (20, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-24'), -- Roshan in MATH101

       (21, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-24'), -- Sabina in CS101
       (21, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-24'), -- Sabina in MATH101

       (22, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-25'), -- Dipesh in CS101
       (22, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-25'), -- Dipesh in MATH101

       (23, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-25'), -- Anjali in CS101
       (23, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-25'), -- Anjali in MATH101

       (24, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-26'), -- Bibek in CS101
       (24, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-26'), -- Bibek in MATH101

       (25, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-26'), -- Pooja in CS101
       (25, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-26'), -- Pooja in MATH101

       (26, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-27'), -- Ritesh in CS101
       (26, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-27'), -- Ritesh in MATH101

       (27, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-27'), -- Shreeya in CS101
       (27, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-27'), -- Shreeya in MATH101

       (28, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-28'), -- Kiran in CS101
       (28, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-28'), -- Kiran in MATH101

       (29, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-28'), -- Manisha in CS101
       (29, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-28'), -- Manisha in MATH101

       (30, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-29'), -- Suraj in CS101
       (30, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-29'), -- Suraj in MATH101

       (31, 1, 'Fall 2024', NULL, 'ENROLLED', '2024-08-29'), -- Priyanka in CS101
       (31, 4, 'Fall 2024', NULL, 'ENROLLED', '2024-08-29'), -- Priyanka in MATH101

       -- Extra enrollments (10) to make total enrollments = 50 (first 10 students also take CS201)
       (12, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-08-30'), -- Aayush in CS201
       (13, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-08-30'), -- Anusha in CS201
       (14, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-08-31'), -- Suman in CS201
       (15, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-08-31'), -- Rojina in CS201
       (16, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-09-01'), -- Prabin in CS201
       (17, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-09-01'), -- Kritika in CS201
       (18, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-09-02'), -- Sagar in CS201
       (19, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-09-02'), -- Nisha in CS201
       (20, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-09-03'), -- Roshan in CS201
       (21, 2, 'Fall 2024', NULL, 'ENROLLED', '2024-09-03');
-- Sabina in CS201

-- Enrollment IDs now are 1..50 in order of insert

-- ===========================
-- ATTENDANCES
-- status ENUM('PRESENT','ABSENT','EXCUSED')
-- NOTE: at least 10 attendance records per student (20 students => 200 records)
-- ===========================
INSERT INTO attendances (enrollment_id, session_date, status, remarks)
VALUES
    -- Student 12 (Aayush): enrollment 1 (CS101) + 2 (MATH101)
    (1, '2024-09-01', 'PRESENT', NULL),
    (1, '2024-09-03', 'ABSENT', 'Bimar'),
    (1, '2024-09-05', 'PRESENT', NULL),
    (1, '2024-09-07', 'PRESENT', NULL),
    (1, '2024-09-09', 'EXCUSED', 'Medical note'),
    (2, '2024-09-02', 'PRESENT', NULL),
    (2, '2024-09-04', 'PRESENT', NULL),
    (2, '2024-09-06', 'ABSENT', 'Ghar ko kaam'),
    (2, '2024-09-08', 'PRESENT', NULL),
    (2, '2024-09-10', 'PRESENT', NULL),

    -- Student 13 (Anusha): enrollment 3 + 4
    (3, '2024-09-01', 'PRESENT', NULL),
    (3, '2024-09-03', 'PRESENT', NULL),
    (3, '2024-09-05', 'EXCUSED', 'Campus kaaryakram'),
    (3, '2024-09-07', 'PRESENT', NULL),
    (3, '2024-09-09', 'PRESENT', NULL),
    (4, '2024-09-02', 'PRESENT', NULL),
    (4, '2024-09-04', 'ABSENT', 'Bus late'),
    (4, '2024-09-06', 'PRESENT', NULL),
    (4, '2024-09-08', 'PRESENT', NULL),
    (4, '2024-09-10', 'EXCUSED', 'Medical note'),

    -- Student 14 (Suman): enrollment 5 + 6
    (5, '2024-09-01', 'ABSENT', 'Parivarik kaam'),
    (5, '2024-09-03', 'PRESENT', NULL),
    (5, '2024-09-05', 'PRESENT', NULL),
    (5, '2024-09-07', 'PRESENT', NULL),
    (5, '2024-09-09', 'PRESENT', NULL),
    (6, '2024-09-02', 'PRESENT', NULL),
    (6, '2024-09-04', 'PRESENT', NULL),
    (6, '2024-09-06', 'EXCUSED', 'Campus kaam'),
    (6, '2024-09-08', 'PRESENT', NULL),
    (6, '2024-09-10', 'PRESENT', NULL),

    -- Student 15 (Rojina): enrollment 7 + 8
    (7, '2024-09-01', 'PRESENT', NULL),
    (7, '2024-09-03', 'PRESENT', NULL),
    (7, '2024-09-05', 'PRESENT', NULL),
    (7, '2024-09-07', 'ABSENT', 'Bimar'),
    (7, '2024-09-09', 'PRESENT', NULL),
    (8, '2024-09-02', 'PRESENT', NULL),
    (8, '2024-09-04', 'EXCUSED', 'Campus kaaryakram'),
    (8, '2024-09-06', 'PRESENT', NULL),
    (8, '2024-09-08', 'PRESENT', NULL),
    (8, '2024-09-10', 'PRESENT', NULL),

    -- Student 16 (Prabin): enrollment 9 + 10
    (9, '2024-09-01', 'PRESENT', NULL),
    (9, '2024-09-03', 'ABSENT', 'Bus issue'),
    (9, '2024-09-05', 'PRESENT', NULL),
    (9, '2024-09-07', 'EXCUSED', 'Medical note'),
    (9, '2024-09-09', 'PRESENT', NULL),
    (10, '2024-09-02', 'PRESENT', NULL),
    (10, '2024-09-04', 'PRESENT', NULL),
    (10, '2024-09-06', 'PRESENT', NULL),
    (10, '2024-09-08', 'ABSENT', 'Parivarik kaam'),
    (10, '2024-09-10', 'PRESENT', NULL),

    -- Student 17 (Kritika): enrollment 11 + 12
    (11, '2024-09-01', 'EXCUSED', 'Medical note'),
    (11, '2024-09-03', 'PRESENT', NULL),
    (11, '2024-09-05', 'PRESENT', NULL),
    (11, '2024-09-07', 'PRESENT', NULL),
    (11, '2024-09-09', 'PRESENT', NULL),
    (12, '2024-09-02', 'PRESENT', NULL),
    (12, '2024-09-04', 'PRESENT', NULL),
    (12, '2024-09-06', 'ABSENT', 'Bimar'),
    (12, '2024-09-08', 'PRESENT', NULL),
    (12, '2024-09-10', 'PRESENT', NULL),

    -- Student 18 (Sagar): enrollment 13 + 14
    (13, '2024-09-01', 'PRESENT', NULL),
    (13, '2024-09-03', 'PRESENT', NULL),
    (13, '2024-09-05', 'ABSENT', 'Family event'),
    (13, '2024-09-07', 'PRESENT', NULL),
    (13, '2024-09-09', 'PRESENT', NULL),
    (14, '2024-09-02', 'PRESENT', NULL),
    (14, '2024-09-04', 'PRESENT', NULL),
    (14, '2024-09-06', 'EXCUSED', 'Campus kaam'),
    (14, '2024-09-08', 'PRESENT', NULL),
    (14, '2024-09-10', 'PRESENT', NULL),

    -- Student 19 (Nisha): enrollment 15 + 16
    (15, '2024-09-01', 'PRESENT', NULL),
    (15, '2024-09-03', 'ABSENT', 'Bimar'),
    (15, '2024-09-05', 'PRESENT', NULL),
    (15, '2024-09-07', 'PRESENT', NULL),
    (15, '2024-09-09', 'PRESENT', NULL),
    (16, '2024-09-02', 'PRESENT', NULL),
    (16, '2024-09-04', 'PRESENT', NULL),
    (16, '2024-09-06', 'PRESENT', NULL),
    (16, '2024-09-08', 'EXCUSED', 'Medical note'),
    (16, '2024-09-10', 'PRESENT', NULL),

    -- Student 20 (Roshan): enrollment 17 + 18
    (17, '2024-09-01', 'PRESENT', NULL),
    (17, '2024-09-03', 'PRESENT', NULL),
    (17, '2024-09-05', 'PRESENT', NULL),
    (17, '2024-09-07', 'EXCUSED', 'Campus kaaryakram'),
    (17, '2024-09-09', 'PRESENT', NULL),
    (18, '2024-09-02', 'PRESENT', NULL),
    (18, '2024-09-04', 'ABSENT', 'Bus issue'),
    (18, '2024-09-06', 'PRESENT', NULL),
    (18, '2024-09-08', 'PRESENT', NULL),
    (18, '2024-09-10', 'PRESENT', NULL),

    -- Student 21 (Sabina): enrollment 19 + 20
    (19, '2024-09-01', 'ABSENT', 'Bus late'),
    (19, '2024-09-03', 'PRESENT', NULL),
    (19, '2024-09-05', 'PRESENT', NULL),
    (19, '2024-09-07', 'PRESENT', NULL),
    (19, '2024-09-09', 'EXCUSED', 'Medical note'),
    (20, '2024-09-02', 'PRESENT', NULL),
    (20, '2024-09-04', 'PRESENT', NULL),
    (20, '2024-09-06', 'ABSENT', 'Ghar ko kaam'),
    (20, '2024-09-08', 'PRESENT', NULL),
    (20, '2024-09-10', 'PRESENT', NULL),

    -- Student 22 (Dipesh): enrollment 21 + 22
    (21, '2024-09-01', 'PRESENT', NULL),
    (21, '2024-09-03', 'PRESENT', NULL),
    (21, '2024-09-05', 'ABSENT', 'Family event'),
    (21, '2024-09-07', 'PRESENT', NULL),
    (21, '2024-09-09', 'PRESENT', NULL),
    (22, '2024-09-02', 'PRESENT', NULL),
    (22, '2024-09-04', 'PRESENT', NULL),
    (22, '2024-09-06', 'EXCUSED', 'Campus kaam'),
    (22, '2024-09-08', 'PRESENT', NULL),
    (22, '2024-09-10', 'PRESENT', NULL),

    -- Student 23 (Anjali): enrollment 23 + 24
    (23, '2024-09-01', 'PRESENT', NULL),
    (23, '2024-09-03', 'ABSENT', 'Bimar'),
    (23, '2024-09-05', 'EXCUSED', 'Medical note'),
    (23, '2024-09-07', 'PRESENT', NULL),
    (23, '2024-09-09', 'PRESENT', NULL),
    (24, '2024-09-02', 'PRESENT', NULL),
    (24, '2024-09-04', 'PRESENT', NULL),
    (24, '2024-09-06', 'PRESENT', NULL),
    (24, '2024-09-08', 'ABSENT', 'Bus issue'),
    (24, '2024-09-10', 'PRESENT', NULL),

    -- Student 24 (Bibek): enrollment 25 + 26
    (25, '2024-09-01', 'PRESENT', NULL),
    (25, '2024-09-03', 'PRESENT', NULL),
    (25, '2024-09-05', 'PRESENT', NULL),
    (25, '2024-09-07', 'ABSENT', 'Parivarik kaam'),
    (25, '2024-09-09', 'PRESENT', NULL),
    (26, '2024-09-02', 'PRESENT', NULL),
    (26, '2024-09-04', 'EXCUSED', 'Campus kaaryakram'),
    (26, '2024-09-06', 'PRESENT', NULL),
    (26, '2024-09-08', 'PRESENT', NULL),
    (26, '2024-09-10', 'PRESENT', NULL),

    -- Student 25 (Pooja): enrollment 27 + 28
    (27, '2024-09-01', 'PRESENT', NULL),
    (27, '2024-09-03', 'PRESENT', NULL),
    (27, '2024-09-05', 'EXCUSED', 'Medical note'),
    (27, '2024-09-07', 'PRESENT', NULL),
    (27, '2024-09-09', 'PRESENT', NULL),
    (28, '2024-09-02', 'PRESENT', NULL),
    (28, '2024-09-04', 'ABSENT', 'Bus issue'),
    (28, '2024-09-06', 'PRESENT', NULL),
    (28, '2024-09-08', 'PRESENT', NULL),
    (28, '2024-09-10', 'PRESENT', NULL),

    -- Student 26 (Ritesh): enrollment 29 + 30
    (29, '2024-09-01', 'ABSENT', 'Family event'),
    (29, '2024-09-03', 'PRESENT', NULL),
    (29, '2024-09-05', 'PRESENT', NULL),
    (29, '2024-09-07', 'PRESENT', NULL),
    (29, '2024-09-09', 'PRESENT', NULL),
    (30, '2024-09-02', 'PRESENT', NULL),
    (30, '2024-09-04', 'PRESENT', NULL),
    (30, '2024-09-06', 'EXCUSED', 'Campus kaam'),
    (30, '2024-09-08', 'PRESENT', NULL),
    (30, '2024-09-10', 'PRESENT', NULL),

    -- Student 27 (Shreeya): enrollment 31 + 32
    (31, '2024-09-01', 'PRESENT', NULL),
    (31, '2024-09-03', 'PRESENT', NULL),
    (31, '2024-09-05', 'ABSENT', 'Bimar'),
    (31, '2024-09-07', 'PRESENT', NULL),
    (31, '2024-09-09', 'EXCUSED', 'Medical note'),
    (32, '2024-09-02', 'PRESENT', NULL),
    (32, '2024-09-04', 'PRESENT', NULL),
    (32, '2024-09-06', 'PRESENT', NULL),
    (32, '2024-09-08', 'ABSENT', 'Bus issue'),
    (32, '2024-09-10', 'PRESENT', NULL),

    -- Student 28 (Kiran): enrollment 33 + 34
    (33, '2024-09-01', 'PRESENT', NULL),
    (33, '2024-09-03', 'EXCUSED', 'Campus kaaryakram'),
    (33, '2024-09-05', 'PRESENT', NULL),
    (33, '2024-09-07', 'PRESENT', NULL),
    (33, '2024-09-09', 'PRESENT', NULL),
    (34, '2024-09-02', 'PRESENT', NULL),
    (34, '2024-09-04', 'ABSENT', 'Ghar ko kaam'),
    (34, '2024-09-06', 'PRESENT', NULL),
    (34, '2024-09-08', 'PRESENT', NULL),
    (34, '2024-09-10', 'PRESENT', NULL),

    -- Student 29 (Manisha): enrollment 35 + 36
    (35, '2024-09-01', 'PRESENT', NULL),
    (35, '2024-09-03', 'ABSENT', 'Bus issue'),
    (35, '2024-09-05', 'PRESENT', NULL),
    (35, '2024-09-07', 'EXCUSED', 'Medical note'),
    (35, '2024-09-09', 'PRESENT', NULL),
    (36, '2024-09-02', 'PRESENT', NULL),
    (36, '2024-09-04', 'PRESENT', NULL),
    (36, '2024-09-06', 'ABSENT', 'Family event'),
    (36, '2024-09-08', 'PRESENT', NULL),
    (36, '2024-09-10', 'PRESENT', NULL),

    -- Student 30 (Suraj): enrollment 37 + 38
    (37, '2024-09-01', 'PRESENT', NULL),
    (37, '2024-09-03', 'PRESENT', NULL),
    (37, '2024-09-05', 'PRESENT', NULL),
    (37, '2024-09-07', 'ABSENT', 'Parivarik kaam'),
    (37, '2024-09-09', 'PRESENT', NULL),
    (38, '2024-09-02', 'PRESENT', NULL),
    (38, '2024-09-04', 'EXCUSED', 'Campus kaam'),
    (38, '2024-09-06', 'PRESENT', NULL),
    (38, '2024-09-08', 'PRESENT', NULL),
    (38, '2024-09-10', 'PRESENT', NULL),

    -- Student 31 (Priyanka): enrollment 39 + 40
    (39, '2024-09-01', 'PRESENT', NULL),
    (39, '2024-09-03', 'ABSENT', 'Bimar'),
    (39, '2024-09-05', 'PRESENT', NULL),
    (39, '2024-09-07', 'PRESENT', NULL),
    (39, '2024-09-09', 'EXCUSED', 'Medical note'),
    (40, '2024-09-02', 'PRESENT', NULL),
    (40, '2024-09-04', 'PRESENT', NULL),
    (40, '2024-09-06', 'ABSENT', 'Bus issue'),
    (40, '2024-09-08', 'PRESENT', NULL),
    (40, '2024-09-10', 'PRESENT', NULL);

-- ===========================
-- FEES
-- type ENUM('TUITION','LAB','LIBRARY','OTHER')
-- amount INT, is_paid BOOLEAN
-- ===========================
INSERT INTO fees (student_id, amount, type, is_paid, due_date, payment_date)
VALUES
    -- Aayush (id 12)
    (12, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-25'),
    (12, 200, 'LAB', FALSE, '2024-09-15', NULL),
    (12, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-05'),

    -- Anusha (id 13)
    (13, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (13, 200, 'LAB', TRUE, '2024-09-15', '2024-09-12'),
    (13, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-06'),

    -- Suman (id 14)
    (14, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-28'),
    (14, 200, 'LAB', TRUE, '2024-09-15', '2024-09-11'),
    (14, 100, 'LIBRARY', FALSE, '2024-09-10', NULL),

    -- Rojina (id 15)
    (15, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (15, 200, 'LAB', FALSE, '2024-09-15', NULL),
    (15, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-07'),

    -- Prabin (id 16)
    (16, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-29'),
    (16, 200, 'LAB', FALSE, '2024-09-15', NULL),
    (16, 100, 'LIBRARY', FALSE, '2024-09-10', NULL),

    -- Kritika (id 17)
    (17, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-30'),
    (17, 200, 'LAB', TRUE, '2024-09-15', '2024-09-12'),
    (17, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-08'),

    -- Sagar (id 18)
    (18, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (18, 200, 'LAB', TRUE, '2024-09-15', '2024-09-09'),
    (18, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-05'),

    -- Nisha (id 19)
    (19, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-27'),
    (19, 200, 'LAB', FALSE, '2024-09-15', NULL),
    (19, 100, 'LIBRARY', FALSE, '2024-09-10', NULL),

    -- Roshan (id 20)
    (20, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (20, 200, 'LAB', TRUE, '2024-09-15', '2024-09-13'),
    (20, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-09'),

    -- Sabina (id 21)
    (21, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-26'),
    (21, 200, 'LAB', TRUE, '2024-09-15', '2024-09-12'),
    (21, 100, 'LIBRARY', TRUE, '2024-09-10', '2024-09-06'),

    -- Dipesh (id 22)
    (22, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (22, 200, 'LAB', FALSE, '2024-09-15', NULL),

    -- Anjali (id 23)
    (23, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-25'),
    (23, 200, 'LAB', TRUE, '2024-09-15', '2024-09-10'),

    -- Bibek (id 24)
    (24, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-29'),
    (24, 200, 'LAB', FALSE, '2024-09-15', NULL),

    -- Pooja (id 25)
    (25, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (25, 200, 'LAB', TRUE, '2024-09-15', '2024-09-14'),

    -- Ritesh (id 26)
    (26, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-30'),
    (26, 200, 'LAB', TRUE, '2024-09-15', '2024-09-11'),

    -- Shreeya (id 27)
    (27, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (27, 200, 'LAB', FALSE, '2024-09-15', NULL),

    -- Kiran (id 28)
    (28, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-28'),
    (28, 200, 'LAB', TRUE, '2024-09-15', '2024-09-12'),

    -- Manisha (id 29)
    (29, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-27'),
    (29, 200, 'LAB', FALSE, '2024-09-15', NULL),

    -- Suraj (id 30)
    (30, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
    (30, 200, 'LAB', TRUE, '2024-09-15', '2024-09-13'),

    -- Priyanka (id 31)
    (31, 1500, 'TUITION', TRUE, '2024-08-31', '2024-08-26'),
    (31, 200, 'LAB', TRUE, '2024-09-15', '2024-09-12');
