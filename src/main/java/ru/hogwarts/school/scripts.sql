select *
from student
where age between 10 and 20;

select name
from student;

select *
from student
where name like '%o%' or name like '%O%';

select *
from student
where age < student.id;

select *
from student
order by age;