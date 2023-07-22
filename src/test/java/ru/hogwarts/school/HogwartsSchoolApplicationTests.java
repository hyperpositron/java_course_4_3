package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsSchoolApplicationTests {

	@Autowired
	private StudentController studentController;
	@Autowired
	private FacultyController facultyController;
	@Autowired
	private AvatarController avatarController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(studentController);
		Assertions.assertNotNull(facultyController);
		Assertions.assertNotNull(avatarController);
	}
}