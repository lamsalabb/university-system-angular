USE university_system;

-- =============================================
-- 1. USERS (Admin, 10 Instructors, 20 Students)
-- =============================================
INSERT INTO users (email, password_hash, role, first_name, last_name, is_active)
VALUES ('admin@uni.edu', 'admin123', 'ADMIN', 'System', 'Admin', TRUE),
-- Instructors (IDs: 2-11)
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
-- Students (IDs: 12-31)
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

-- =============================================
-- 2. COURSES (with Cost)
-- =============================================
INSERT INTO courses (code, title, credits, instructor_id, cost, description)
VALUES ('CS101', 'Intro to Computing', 3, 2, 5000, 'Basics of computer science'),
       ('CS201', 'Data Structures', 4, 2, 6000, 'Core data structures'),
       ('CS301', 'Algorithms', 4, 8, 6500, 'Algorithm design'),
       ('MATH101', 'Calculus I', 4, 3, 4000, 'Limits and derivatives'),
       ('MATH201', 'Linear Algebra', 4, 4, 4000, 'Matrices and vectors'),
       ('ENG101', 'English Comp', 3, NULL, 2500, 'Academic writing'),
       ('ENG201', 'Tech Writing', 3, 7, 3000, 'Technical documentation'),
       ('MGMT101', 'Intro to Mgmt', 3, 6, 3500, 'Leadership basics'),
       ('PHY101', 'Physics I', 4, NULL, 4500, 'Mechanics and waves'),
       ('STAT101', 'Statistics', 3, 9, 3800, 'Probability basics');

-- =============================================
-- 3. ENROLLMENTS (Fall 2024)
-- =============================================
-- All 20 students take CS101 and MATH101
INSERT INTO enrollments (student_id, course_id, semester, status, enrollment_date)
SELECT u.id, c.id, 'Fall 2024', 'ENROLLED', '2024-08-20'
FROM users u,
     courses c
WHERE u.role = 'STUDENT'
  AND c.code IN ('CS101', 'MATH101');

-- First 10 students (IDs 12-21) also take CS201
INSERT INTO enrollments (student_id, course_id, semester, status, enrollment_date)
SELECT id, 2, 'Fall 2024', 'ENROLLED', '2024-08-30'
FROM users
WHERE role = 'STUDENT'
LIMIT 10;

-- =============================================
-- 4. ATTENDANCE (Sample for Enrollment #1)
-- =============================================
INSERT INTO attendances (enrollment_id, session_date, status, remarks)
VALUES (1, '2024-09-01', 'PRESENT', NULL),
       (1, '2024-09-03', 'ABSENT', 'Medical'),
       (1, '2024-09-05', 'PRESENT', NULL),
       (2, '2024-09-01', 'PRESENT', NULL),
       (2, '2024-09-03', 'PRESENT', NULL);
-- (Note: In a real system, you'd use a script to loop through all enrollments)

-- =============================================
-- 5. FEES (Linked to Students)
-- =============================================
INSERT INTO fees (student_id, amount, type, is_paid, due_date, payment_date)
SELECT id,
       1500,
       'TUITION',
       (id % 2 = 0),
       '2024-08-31',
       CASE WHEN id % 2 = 0 THEN '2024-08-25' ELSE NULL END
FROM users
WHERE role = 'STUDENT';