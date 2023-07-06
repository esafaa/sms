package jpa.entitymodels;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/*The first constructor takes no parameters and initializes every member to an initial value.
The second constructor must initialize every private member with a parameter provided to the constructor.
Create a class Course with the private member variables(cId, cName, cInstructorName) 
These private members must have GETTERS and SETTERS methods.
The purpose of the Course class is to carry data related to one Course.

*/
@Entity
public class Course implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int cId;
	@Column(name = "name",nullable = false)
	private String cName;
	@Column(name = "Instructor", nullable = false)
	private String cInstructorName;

	// Constructor
	public Course() {

	}

	public Course(String cName, String cInstructorName) {
		this.cName = cName;
		this.cInstructorName = cInstructorName;
	}

	// Getters and Setters

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcInstructorName() {
		return cInstructorName;
	}

	public void setcInstructorName(String cInstructorName) {
		this.cInstructorName = cInstructorName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cId;
		result = prime * result + ((cInstructorName == null) ? 0 : cInstructorName.hashCode());
		result = prime * result + ((cName == null) ? 0 : cName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		if (cId != other.cId)
			return false;
		if (cInstructorName == null) {
			if (other.cInstructorName != null)
				return false;
		} else if (!cInstructorName.equals(other.cInstructorName))
			return false;
		if (cName == null) {
			if (other.cName != null)
				return false;
		} else if (!cName.equals(other.cName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return String.format("%-5s%-30S%-25s\n",cId, cName, cInstructorName);
	}
	
	

}
