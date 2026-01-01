USE university_system;

-- Optional: clear existing data in correct FK order
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM fees;
DELETE FROM attendances;
DELETE FROM enrollments;
DELETE FROM courses;
DELETE FROM users;
SET FOREIGN_KEY_CHECKS = 1;

-- ===========================
-- USERS
-- ===========================
INSERT INTO users (email, password_hash, role, first_name, last_name, is_active)
VALUES
  ('admin@uni.edu',         'admin123',   'ADMIN',     'System',  'Admin',    TRUE),
  ('john.doe@uni.edu',      'instr123',   'INSTRUCTOR','John',    'Doe',      TRUE),
  ('mary.smith@uni.edu',    'instr123',   'INSTRUCTOR','Mary',    'Smith',    TRUE),
  ('alice.johnson@uni.edu', 'stud123',    'STUDENT',   'Alice',   'Johnson',  TRUE),
  ('bob.williams@uni.edu',  'stud123',    'STUDENT',   'Bob',     'Williams', TRUE),
  ('charlie.brown@uni.edu', 'stud123',    'STUDENT',   'Charlie', 'Brown',    TRUE);

-- IDs now are:
-- 1 = admin, 2 = John (instr), 3 = Mary (instr), 4/5/6 = students

-- ===========================
-- COURSES
-- ===========================
INSERT INTO courses (code, title, credits, instructor_id, description)
VALUES
  ('CS101',   'Intro to Computer Science',  3, 2, 'Basics of computing concepts'),
  ('CS201',   'Data Structures',            4, 2, 'Core data structures course'),
  ('MATH101', 'Calculus I',                 4, 3, 'Introductory calculus'),
  ('ENG101',  'English Composition',        3, NULL, 'Academic writing skills');

-- IDs now are:
-- 1 = CS101, 2 = CS201, 3 = MATH101, 4 = ENG101

-- ===========================
-- ENROLLMENTS
-- (student_id, course_id, semester must be unique)
-- ===========================
INSERT INTO enrollments (student_id, course_id, semester, grade, status, enrollment_date)
VALUES
  (4, 1, 'Fall 2024', NULL, 'ENROLLED',  '2024-08-20'), -- Alice in CS101
  (4, 2, 'Fall 2024', NULL, 'ENROLLED',  '2024-08-20'), -- Alice in CS201
  (5, 1, 'Fall 2024', NULL, 'ENROLLED',  '2024-08-21'), -- Bob in CS101
  (5, 3, 'Fall 2024', NULL, 'ENROLLED',  '2024-08-21'), -- Bob in MATH101
  (6, 1, 'Fall 2024', NULL, 'ENROLLED',  '2024-08-22'), -- Charlie in CS101
  (6, 4, 'Fall 2024', NULL, 'DROPPED',   '2024-08-22'); -- Charlie in ENG101 (dropped)

-- Enrollment IDs now are 1..6 in order of insert

-- ===========================
-- ATTENDANCES
-- status ENUM('PRESENT','ABSENT','EXCUSED')
-- ===========================
INSERT INTO attendances (enrollment_id, session_date, status, remarks)
VALUES
  (1, '2024-09-01', 'PRESENT', NULL),
  (1, '2024-09-03', 'ABSENT',  'Sick'),
  (2, '2024-09-01', 'PRESENT', NULL),
  (3, '2024-09-01', 'PRESENT', NULL),
  (3, '2024-09-03', 'EXCUSED', 'Medical note'),
  (4, '2024-09-02', 'PRESENT', NULL),
  (5, '2024-09-01', 'ABSENT',  'Family event'),
  (5, '2024-09-03', 'PRESENT', NULL),
  (6, '2024-09-01', 'EXCUSED', 'Dropped later'),
  (6, '2024-09-05', 'ABSENT',  'No longer attending');

-- ===========================
-- FEES
-- type ENUM('TUITION','LAB','LIBRARY','OTHER')
-- amount INT, is_paid BOOLEAN
-- ===========================
INSERT INTO fees (student_id, amount, type, is_paid, due_date, payment_date)
VALUES
  -- Alice (id 4)
  (4, 1500, 'TUITION', TRUE,  '2024-08-31', '2024-08-25'),
  (4,  200, 'LAB',     FALSE, '2024-09-15', NULL),

  -- Bob (id 5)
  (5, 1500, 'TUITION', FALSE, '2024-08-31', NULL),
  (5,  100, 'LIBRARY', TRUE,  '2024-09-10', '2024-09-05'),

  -- Charlie (id 6)
  (6, 1500, 'TUITION', TRUE,  '2024-08-31', '2024-08-30'),
  (6,  150, 'LAB',     TRUE,  '2024-09-15', '2024-09-12'),
  (6,   50, 'LIBRARY', FALSE, '2024-09-20', NULL),
  (6,  100, 'OTHER',   FALSE, '2024-09-25', NULL);
