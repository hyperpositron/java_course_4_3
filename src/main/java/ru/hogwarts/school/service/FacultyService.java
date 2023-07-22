package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.ElementNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            return facultyRepository.save(faculty);
        }
        throw new ElementNotFoundException();
    }

    public void removeFaculty(Long id) {
        facultyRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        facultyRepository.deleteById(id);
    }

    public Faculty getFacultyByColor(String color) {
        Faculty faculty = facultyRepository.findFirstFacultyByColor(color);
        if (faculty == null) {
            throw new ElementNotFoundException();
        }
        return faculty;
    }

    public Faculty getFacultyByColorOrName(String colorOrName) {
        Faculty faculty = facultyRepository.findFirstFacultyByColorOrName(colorOrName, colorOrName);
        if (faculty == null) {
            throw new ElementNotFoundException();
        }
        return faculty;
    }

    public Collection<Student> getFacultyStudents(Long id) {
        return getFaculty(id).getStudents();
    }
}