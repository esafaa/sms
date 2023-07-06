package jpa.entitymodels;

import java.io.Serializable;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

/*The first constructor takes no parameters and initializes every member to an initial value.
The second constructor must initialize every private member with a parameter provided to the constructor.
Create a class Student with the private member variables (sEmail, sName, sPass(Password),List of Course). 
These private members must have GETTERS and SETTERS methods.
The purpose of the Student class is to carry data related to one student
*/

@Entity
public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "email", length = 50)
	private String sEmail;
	@Column(name = "name", nullable = false, length = 50)
	private String sName;
	@Column(name = "password", nullable = false, length = 50)
	private String sPass;
	@ManyToMany(targetEntity = Course.class, cascade = { CascadeType.ALL })
	@JoinTable(name = "student_course", joinColumns = { @JoinColumn(name = "sEmail") }, inverseJoinColumns = {
			@JoinColumn(name = "cId") })
	private List<Course> sCourses;

	// Constructors
	public Student() {
	}

	public Student(String sEmail, String sName, String sPass, List<Course> sCourses) {

		this.sEmail = sEmail;
		this.sName = sName;
		this.sPass = sPass;
		this.sCourses = sCourses;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsPass() {
		return sPass;
	}

	public void setsPass(String sPass) {
		this.sPass = sPass;
	}

	public List<Course> getsCourses() {
		return sCourses;
	}

	public void setsCourses(List<Course> sCourses) {
		this.sCourses = sCourses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sCourses == null) ? 0 : sCourses.hashCode());
		result = prime * result + ((sEmail == null) ? 0 : sEmail.hashCode());
		result = prime * result + ((sName == null) ? 0 : sName.hashCode());
		result = prime * result + ((sPass == null) ? 0 : sPass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		if (sCourses == null) {
			if (other.sCourses != null)
				return false;
		} else if (!sCourses.equals(other.sCourses))
			return false;
		if (sEmail == null) {
			if (other.sEmail != null)
				return false;
		} else if (!sEmail.equals(other.sEmail))
			return false;
		if (sName == null) {
			if (other.sName != null)
				return false;
		} else if (!sName.equals(other.sName))
			return false;
		if (sPass == null) {
			if (other.sPass != null)
				return false;
		} else if (!sPass.equals(other.sPass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Name: %-30s|Email: %-30s\n", sName, sEmail);
	}

}
