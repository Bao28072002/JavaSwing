package model;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
	List<Student> list = new ArrayList<>();

	public StudentManager() {
		this.list = list;
	}

	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}

	public void insert(Student stu) {
		this.list.add(stu);
	}

	public void delete(Student stu) {
		this.list.remove(stu);
	}

	public void update(Student stu) {
		this.list.remove(stu);
		this.list.add(stu);
	}
}
