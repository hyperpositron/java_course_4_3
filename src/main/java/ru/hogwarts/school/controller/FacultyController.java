package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.BadParamsException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("add")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("get/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @DeleteMapping("remove/{id}")
    public void removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
    }

    @PutMapping("update")
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        checkParametersForNull(faculty.getId());
        return facultyService.updateFaculty(faculty);
    }

    @GetMapping(value = "get", params = {"color"})
    public Faculty getFacultyByColor(@RequestParam(required = false) String color) {
        checkParametersForNull(color);
        return facultyService.getFacultyByColor(color.trim());
    }

    @GetMapping(value = "get", params = {"colorOrName"})
    public Faculty getFacultyByColorOrName(@RequestParam(required = false) String colorOrName) {
        checkParametersForNull(colorOrName);
        return facultyService.getFacultyByColorOrName(colorOrName.trim());
    }

    @GetMapping("{id}/getStudents")
    public Collection<Student> getFacultyStudents(@PathVariable Long id) {
        return facultyService.getFacultyStudents(id);
    }

    private void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BadParamsException();
            }
        }
    }
}