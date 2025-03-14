package model;

import java.time.LocalDate;

public class Student {
	private String id;
	private String name;
	private boolean gender;
	private LocalDate dob;
	private String diaChi;
	private String idclass;
	private Faculty facultyInfo;

	public Student() {

	}

	public Student(String id, String name, boolean gender, LocalDate dob, String diaChi, String idclass,
			Faculty facultyInfo) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.diaChi = diaChi;
		this.idclass = idclass;
		this.facultyInfo = facultyInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getIdclass() {
		return idclass;
	}

	public void setIdclass(String idclass) {
		this.idclass = idclass;
	}

	public Faculty getFacultyInfo() {
		return facultyInfo;
	}

	public void setFacultyInfo(Faculty facultyInfo) {
		this.facultyInfo = facultyInfo;
	}



}
