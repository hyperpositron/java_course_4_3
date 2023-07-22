package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.BadParamsException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("get/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @DeleteMapping("remove/{id}")
    public void removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
    }

    @PutMapping("update")
    public Student updateStudent(@RequestBody Student student) {
        checkParametersForNull(student.getId());
        return studentService.updateStudent(student);
    }

    @GetMapping(value = "get", params = "age")
    public Collection<Student> getStudentsByAge(@RequestParam (required = false) Integer age) {
        checkParametersForNull(age);
        return studentService.getStudentsByAge(age);
    }

    @GetMapping(value = "get", params = {"minAge", "maxAge"})
    public Collection<Student> getStudentsByAgeBetween(@RequestParam (required = false) Integer minAge, @RequestParam(required = false) Integer maxAge) {
        checkParametersForNull(minAge, maxAge);
        return studentService.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("{id}/getFaculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }

    private void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BadParamsException();
            }
        }
    }
}