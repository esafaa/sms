
/*
 * Filename: SMSRunner.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.mainrunner;

import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;
import jpa.utils.HibernateUtils;

/**
 * 1
 * 
 * @author Harry
 *
 */

/*
 * School Management System where students can register for courses, and view
 * the course assigned to them.
 */
public class SMSRunner {

	private Scanner sin;
	private StringBuilder sb;

	private CourseService courseService;
	private StudentService studentService;
	private Student currentStudent;

	public SMSRunner() {
		sin = new Scanner(System.in);
		sb = new StringBuilder();
		courseService = new CourseService();
		studentService = new StudentService();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SMSRunner sms = new SMSRunner();
		sms.run();
	}

	private void run() {
		// Login or quit
		switch (menu1()) {
		case 1:
			if (studentLogin()) {
				registerMenu();
			}
			break;
		case 2:
			System.out.println("Goodbye!");
			break;

		default:
			System.out.println("Invalid Entry, Goodbye!");

		}
	}

	private int menu1() {
		sb.append("\n1.Student Login\n2. Quit Application\nPlease Enter Selection: ");
		System.out.print(sb.toString());
		sb.delete(0, sb.length());
		try {
			return sin.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid Entry");
			return 2;
		}
	}

	private boolean studentLogin() {
		boolean retValue = false;
		System.out.print("Enter your email address: ");
		String email = sin.next();
		System.out.print("Enter your password: ");
		String password = sin.next();
		/*
		 * getStudentByEmail method will return only one student
		 */
		Student student = studentService.getStudentByEmail(email);

		if (student != null) {
			currentStudent = student;
		}
		/*
		 * Validation with the method getStudentCourses() to validate the existence of
		 * the student with the given email and password
		 */
		if (studentService.validateStudent(email, password)) {
			List<Course> courses = studentService.getStudentCourses(email);
			/*
			 * check if the student is enrolled in any courses before printing the list
			 */
			if (!courses.isEmpty()) {
				System.out.println("You are currently enrolled in: ");
				System.out.printf("%-5s%-30S%-25s\n", "ID", "Course", "Instructor");
				for (Course course : courses) {
					System.out.println(course);
				}
			} else {
				System.out.println("You are not enrolled in any courses yet, please select:");
			}
			retValue = true;
		} else {
			System.out.println("Wrong Credentials. GoodBye!");// will print User Validation failed
			HibernateUtils.closeSessionFactory();

		}
		return retValue;
	}

	private void registerMenu() {
		try {
			sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
			System.out.print(sb.toString());
			sb.delete(0, sb.length());

			switch (sin.nextInt()) {
			case 1:
				List<Course> allCourses = courseService.getAllCourses();
				List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getsEmail());
				allCourses.removeAll(studentCourses);

				/*
				 * If the student is enrolled in all courses will exit the program after
				 * printing a message for the student
				 */
				if (allCourses.isEmpty()) {
					System.out.println("You are enrolled in all courses, no more courses for now!");
					System.out.println("You are signed out");
					HibernateUtils.closeSessionFactory();
					return;
				}
				/* Else it print all the courses */
				System.out.printf("%-5s%-30S%-25s\n", "ID", "Course", "Instructor");
				for (Course course : allCourses) {
					System.out.println(course);
				}
				System.out.println();
				System.out.print("Enter Course Number: ");

				int number = sin.nextInt();

				Course newCourse = courseService.GetCourseById(number);

				if (newCourse != null) {

					studentService.registerStudentToCourse(currentStudent.getsEmail(), newCourse.getcId());
					List<Course> sCourses = studentService.getStudentCourses(currentStudent.getsEmail());
					;

					System.out.println("List of courses you are enrolled in:");
					System.out.printf("%-5s%-30S%-25s\n", "ID", "Course", "Instructor");

					for (Course course : sCourses) {
						System.out.println(course);
					}
					System.out.println("Your are signed out");
					HibernateUtils.closeSessionFactory();// close the sessionFactory()
				}else {
					System.out.println("Invalid course ID");
					System.out.println("Your are signed out");
					HibernateUtils.closeSessionFactory();// close the sessionFactory()

				}

				break;

			case 2: System.out.println("Your are signed out.");
			HibernateUtils.closeSessionFactory();// close the sessionFactory()

			break;
			default: {
				System.out.println("Wrong Entry");
				System.out.println("Goodbye!");
				HibernateUtils.closeSessionFactory();// close the sessionFactory()
			}
			}
		} catch (Exception e) {
			System.out.println("Invalid Entry, GoodBye!");
			HibernateUtils.closeSessionFactory();// close the sessionFactory()

		}
	}
}
