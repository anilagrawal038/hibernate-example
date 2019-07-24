package com.san.dao6;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.san.config.HibernateUtil;
import com.san.entity.Student;

public class StudentDAO<T> implements EntityDAO<T> {

	private static StudentDAO<Student> _studentDAO;

	private StudentDAO() {
	}

	public static StudentDAO<Student> getStudentDAO() {
		if (_studentDAO == null) {
			_studentDAO = new StudentDAO<Student>();
		}
		return _studentDAO;
	}

	@Override
	public T get(Serializable entityId) {
		return get(entityId, Student.class);
	}

	@Override
	public List<T> list() {
		return list(Student.class);
	}

	public Student findByFirstName(String firstName) {
		Session session = HibernateUtil.openSession();
		Query<Student> query = (Query<Student>) session.createQuery("from Student where firstName=:firstName", Student.class);
		query.setParameter("firstName", firstName);
		List<Student> resultSet = (List<Student>) query.setMaxResults(1).list();
		session.close();
		Student entity = null;
		if (!resultSet.isEmpty()) {
			entity = resultSet.get(0);
		}
		return entity;
	}

}
