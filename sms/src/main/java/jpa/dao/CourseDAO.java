package jpa.dao;

import java.util.List;

import jpa.entitymodels.Course;

public interface CourseDAO {

	/*
	 * getAllCourses: This method takes no parameters and returns every Course in
	 * the table.
	 */
	List<Course> getAllCourses();
}
