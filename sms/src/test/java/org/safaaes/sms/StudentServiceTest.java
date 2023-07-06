package org.safaaes.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;

public class StudentServiceTest {
	private StudentService studentService;
	private Student student;
	private List<Course> courses = new ArrayList<Course>();

	@Before
	public void setUp() throws Exception {
		studentService = new StudentService();
		student = new Student();
		student.setsEmail("qllorens2@howstuffworks.com");
		student.setsName("Quillan Llorens");
		student.setsPass("W6rJuxd");
		Course courseArt = new Course("Art", "Kingsly Doxsey");
		courseArt.setcId(10);
		courses.add(courseArt);
		Course courseEnglish = new Course("English", "Anderea Scamaden");
		courseEnglish.setcId(1);
		courses.add(courseEnglish);
        student.setsCourses(courses);

	}

	/* Positive Test */
	@Test
	public void should_Return_True_validateStudent() {
		// given
		String email = student.getsEmail();
		String password = student.getsPass();

		// when
		boolean actual = studentService.validateStudent(email, password);
		// then
		assertTrue(actual);
	}

	/* Negative Test */
	@ParameterizedTest
	@CsvSource(value = { ",", ",wrongPassword", "wrongEmail,", "wrongEmail,wrongPassword" })
	public void should_Return_False_validateStudent(String sEmail, String sPassword) {
		// given
		String email = sEmail;
		String password = sPassword;
		StudentService studentService = new StudentService();
		// when
		boolean actual = studentService.validateStudent(email, password);
		// then
		assertFalse(actual);
	}
	
	

	/* Test method getStudentByEmail */

	@Test
	public void Should_return_True_For_GetStudentByEmail() {
		// given
		String email = student.getsEmail();

		// when
		Student expectedStudent = studentService.getStudentByEmail(email);
		
		// then
		assertEquals(student, expectedStudent);
	}
}
