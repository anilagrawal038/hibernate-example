package com.san.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.san.config.HibernateUtil;

public interface EntityDAO<T> {

	default public T save(T entity) {
		Session session = HibernateUtil.openSession();
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		session.close();
		return entity;
	}

	default public T update(T entity) {
		Session session = HibernateUtil.openSession();
		Transaction tx = session.beginTransaction();
		session.update(entity);
		tx.commit();
		session.close();
		return entity;
	}

	public T get(Serializable entityId);

	@SuppressWarnings("unchecked")
	default public T get(Serializable entityId, Class<?> entityClass) {
		Session session = HibernateUtil.openSession();
		T entity = (T) session.createCriteria(entityClass).add(Restrictions.eq("id", entityId)).uniqueResult();
		session.close();
		return entity;
	}

	public List<T> list();

	@SuppressWarnings("unchecked")
	default public List<T> list(Class<?> entityClass) {
		Session session = HibernateUtil.openSession();
		List<T> resultSet = (List<T>) session.createQuery("from " + entityClass.getSimpleName()).list();
		session.close();
		return resultSet;
	}

	default public void delete(T entity) {
		Session session = HibernateUtil.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
		session.close();
	}

}
