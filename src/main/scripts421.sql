-- Возраст студента не может быть меньше 16 лет.
alter table student add constraint age_constraint check ( age>=16 );
--Имена студентов должны быть уникальными и не равны нулю.
alter table student alter column name set not null;
alter table student add constraint name_constraint unique (name);
--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty add constraint name_color_constraint unique (name,color);
--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table student alter column age set default 20;