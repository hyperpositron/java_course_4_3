package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepositoryMock;
    @SpyBean
    private FacultyService facultyService;

    private final Long facultyId = 1L;
    private final String facultyName = "testFaculty";
    private final String facultyColor = "testColor";
    private final JSONObject facultyObject = new JSONObject();
    private final Faculty faculty = new Faculty();

    @BeforeEach
    public void init() throws Exception {
        facultyObject.put("id", facultyId);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", facultyColor);

        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);
    }

    @Test
    void addFacultyTest() throws Exception {
        when(facultyRepositoryMock.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.post("/faculty/add")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));
        verify(facultyRepositoryMock, new Times(1)).save(any());
    }

    @Test
    void getFacultyTest() throws Exception {
        when(facultyRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/get/{id}", facultyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));
        verify(facultyRepositoryMock, new Times(1)).findById(any());
    }

    @Test
    void removeFaculty() throws Exception {
        when(facultyRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/remove/{id}", facultyId))
                .andExpect(status().isOk());
        verify(facultyRepositoryMock, new Times(1)).findById(any());
        verify(facultyRepositoryMock, new Times(1)).deleteById(any());
    }

    @Test
    void updateFaculty() throws Exception {
        when(facultyRepositoryMock.existsById(facultyId)).thenReturn(true);
        when(facultyRepositoryMock.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/update")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));
        verify(facultyRepositoryMock, new Times(1)).existsById(any());
        verify(facultyRepositoryMock, new Times(1)).save(any());
    }

    @Test
    void getFacultyByColor() throws Exception {
        when(facultyRepositoryMock.findFirstFacultyByColor(any(String.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/get?color=" + facultyColor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));
        verify(facultyRepositoryMock, new Times(1)).findFirstFacultyByColor(any());
    }

    @Test
    void getFacultyByColorOrName() throws Exception {
        when(facultyRepositoryMock.findFirstFacultyByColorOrName(any(String.class), any(String.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/get?colorOrName=" + facultyColor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor))
                .andExpect(jsonPath("$.id").value(facultyId));
        verify(facultyRepositoryMock, new Times(1)).findFirstFacultyByColorOrName(any(), any());
    }

    @Test
    void getFacultyStudents() throws Exception {
        faculty.setStudents(List.of(
                new Student(1L, "testStudent1", 12),
                new Student(2L, "testStudent2", 13),
                new Student(3L, "testStudent3", 14)));
        when(facultyRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}/getStudents", facultyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("testStudent2"))
                .andExpect(jsonPath("$[1].age").value(13))
                .andExpect(jsonPath("$[1].id").value(2L));
        verify(facultyRepositoryMock, new Times(1)).findById(any());
    }
}