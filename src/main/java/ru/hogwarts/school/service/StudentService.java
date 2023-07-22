package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.ElementNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    public boolean existsStudent(long id) {
        return studentRepository.existsById(id);
    }

    public Student updateStudent(Student student) {
        if (existsStudent(student.getId())) {
            return studentRepository.save(student);
        }
        throw new ElementNotFoundException();
    }

    public void removeStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ElementNotFoundException();
        }
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFaculty(Long id) {
        return getStudent(id).getFaculty();
    }
}