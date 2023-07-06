package jpa.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.utils.HibernateUtils;



public class StudentService implements StudentDAO {
	SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

	/* Retrieve all student from the Database */
	public List<Student> getAllStudents() {
		Session session = sessionFactory.openSession();
		String hql = "from Student ";
		TypedQuery<Student> typedQuery = session.createQuery(hql, Student.class);
		List<Student> result = typedQuery.getResultList();
		session.close();
		return result;

	}

	/*
	 * Retrieve the student registered by primary Key Email, will return only one
	 * student since email is Primary Key
	 */
	public Student getStudentByEmail(String sEmail) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			// Student result = session.get(Student.class, sEmail);
			String hql = "SELECT s FROM Student s LEFT JOIN FETCH s.sCourses WHERE s.sEmail = :email ";
			TypedQuery<Student> typedQuery = session.createQuery(hql, Student.class);
			typedQuery.setParameter("email", sEmail);
			Student student = typedQuery.getSingleResult();
			session.close();
			return student;
		} catch (Exception e) {
			session.close();
			return null;
		}

	}

	/*
	 * This method validate the student based on the combination of email and
	 * password will return true if the student exist in the database False if the
	 * query returns null
	 */
	public boolean validateStudent(String sEmail, String sPassword) {
		Session session = sessionFactory.openSession();
		// Retrieve student from database with email and password
		String hql = "from Student where sEmail= :email and sPass= :password";
		TypedQuery<Student> typedQuery = session.createQuery(hql, Student.class);
		typedQuery.setParameter("email", sEmail);
		typedQuery.setParameter("password", sPassword);
		try {
			Student result = typedQuery.getSingleResult();
			if (result != null) {
				session.close();
				return true;
			}
			return false;
		} catch (NoResultException e) { // if no student exist in the database with email and password
			session.close();
			return false; // return
		}

	}

	/* This method enroll student in a new course */
	/*Valid students are able to register for any course in the system as long as they are not already registered.*/


	public void registerStudentToCourse(String sEmail, int cId) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			// Retrieve the course from the database based on the cId Primary key
			Course course = session.get(Course.class, cId);
			// If the course with the given Id doesn't exist will print course not found and
			// exit
			if (course == null) {
				System.out.println("Course not found.");
				System.out.println("Goodbye!");
				session.close();
			} else {
				// Return a list of courses the student is enrolled in
				Student student = session.get(Student.class, sEmail);
				List<Course> courseList = student.getsCourses();
				/* Check if the list of courses contains already the course selected */
				if (courseList.contains(course)) {
					System.out.println("You are already registered in that course: " + course.getcName());
					session.close();

				} else {
					// Retrieve the student from the database based on the email
					// Add the course to the student's list of courses
					student.getsCourses().add(course);

					// Update the student entity in the database
					session.merge(student);

					transaction.commit();
					System.out.println("You have successfully enrolled in this course: " + course.getcName());
					session.close();
				}
			}
		} catch (Exception e) {
			if (transaction != null) {
				System.out.println("Sorry, we couldn't enroll you in this course try again later");
				transaction.rollback();
				session.close();

			}
			e.printStackTrace();
		}

	}

	/* This method return the list of courses the student is enrolled in */
	/*Valid students are able to see the courses they are registered for.*/


	public List<Course> getStudentCourses(String sEmail) {

		Session session = sessionFactory.openSession();
		/*
		 * Will return student and courses even if the student doesn't have any course
		 * yet
		 */
		String hql = "SELECT s FROM Student s LEFT JOIN FETCH s.sCourses WHERE s.sEmail = :email";
		TypedQuery<Student> typedQuery = session.createQuery(hql, Student.class);
		typedQuery.setParameter("email", sEmail);
		Student student = typedQuery.getSingleResult();

		List<Course> courseList = student.getsCourses();
		if (courseList == null) {
			System.out.println("No courses found");
			session.close();

			return null;
		} else {
			session.close();

			return courseList;

		}
	}
}
