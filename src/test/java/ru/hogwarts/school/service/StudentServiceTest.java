package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.ElementNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    public final StudentRepository studentRepositoryMock = mock(StudentRepository.class);
    public final StudentService out = new StudentService(studentRepositoryMock);

    @Test
    void ElementNotFoundException() {
        when(studentRepositoryMock.findById(any())).thenReturn(Optional.empty());
        Long id = 0L;
        assertThrows(ElementNotFoundException.class, () -> out.getStudent(id));

        when(studentRepositoryMock.existsById(any())).thenReturn(false).thenReturn(false);
        assertThrows(ElementNotFoundException.class, () -> out.updateStudent(new Student()));
        assertThrows(ElementNotFoundException.class, () -> out.removeStudent(id));
    }
}