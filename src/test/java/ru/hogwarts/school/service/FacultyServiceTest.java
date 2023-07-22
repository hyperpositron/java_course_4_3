package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.ElementNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    public final FacultyRepository facultyRepositoryMock = mock(FacultyRepository.class);
    public final FacultyService out = new FacultyService(facultyRepositoryMock);

    @Test
    void ElementNotFoundException() {
        when(facultyRepositoryMock.findById(any())).thenReturn(Optional.empty());
        Long id = 0L;
        assertThrows(ElementNotFoundException.class, () -> out.getFaculty(id));

        when(facultyRepositoryMock.existsById(any())).thenReturn(false).thenReturn(false);
        assertThrows(ElementNotFoundException.class, () -> out.updateFaculty(new Faculty()));
        assertThrows(ElementNotFoundException.class, () -> out.removeFaculty(id));

        String color = "red";
        when(facultyRepositoryMock.findFirstFacultyByColor(any())).thenReturn(null);
        assertThrows(ElementNotFoundException.class, ()-> out.getFacultyByColor(color));
    }
}