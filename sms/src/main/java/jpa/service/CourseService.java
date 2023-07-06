package jpa.service;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.TypedQuery;
import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.utils.HibernateUtils;

public class CourseService implements CourseDAO {

	SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

	/* This method will return all the courses */
	public List<Course> getAllCourses() {

		Session session = sessionFactory.openSession();
		String hql = "select distinct c from Course c";
		TypedQuery<Course> typedQuery = session.createQuery(hql, Course.class);
		List<Course> result = typedQuery.getResultList();
		session.close();
		return result;

	}

	public Course GetCourseById(int cId) {
		Session session = sessionFactory.openSession();
		// Retrieve the course correspondent to the primary key cId
		Course course = session.get(Course.class, cId);
		session.close();
		return course;
	}
}
