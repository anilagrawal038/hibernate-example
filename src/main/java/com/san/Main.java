package com.san;

import com.san.config.HibernateUtil;
import com.san.dao.StudentDAO;
import com.san.entity.Student;

public class Main {
	public static void main(String[] args) {
		Student student = new Student();
		student.setFirstName("f1");
		student.setLastName("l1");
		student.setEmail("e1@interra.com");
		System.out.println("Generated id : " + StudentDAO.getStudentDAO().save(student).getId());
		for (Student s : StudentDAO.getStudentDAO().list()) {
			System.out.println(s);
		}
		System.out.println("fetch for id 5 : " + StudentDAO.getStudentDAO().get(5l));
		System.out.println("fetch for firstName f1 : " + StudentDAO.getStudentDAO().findByFirstName("f1"));
		HibernateUtil.shutdown();
	}
}
