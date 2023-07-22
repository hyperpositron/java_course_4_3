SELECT student.name, student.age, faculty.name
from student
JOIN faculty
on student.faculty_id=faculty.id;

SELECT student.*
from student
JOIN avatar
on avatar.student_id=student.id;