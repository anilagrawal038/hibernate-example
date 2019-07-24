package com.san.dao6;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
		Query<T> query = (Query<T>) session.createQuery("from " + entityClass.getSimpleName() + " where id=:entityId", entityClass);
		query.setParameter("entityId", entityId);
		T entity = (T) query.uniqueResult();
		session.close();
		return entity;
	}

	@SuppressWarnings("unchecked")
	default public T _get(Serializable entityId, Class<?> entityClass) {
		Session session = HibernateUtil.openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(entityClass);
		Root<T> root = (Root<T>) criteriaQuery.from(entityClass);
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), entityId));
		T entity = session.createQuery(criteriaQuery).uniqueResult();
		session.close();
		return entity;
	}

	public List<T> list();

	@SuppressWarnings("unchecked")
	default public List<T> list(Class<?> entityClass) {
		Session session = HibernateUtil.openSession();
		List<T> resultSet = (List<T>) session.createQuery("from " + entityClass.getSimpleName(), entityClass).list();
		session.close();
		return resultSet;
	}

	@SuppressWarnings("unchecked")
	default public List<T> _list(Class<?> entityClass) {
		Session session = HibernateUtil.openSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(entityClass);
		criteriaQuery.from(entityClass);
		Query<T> query = session.createQuery(criteriaQuery);
		List<T> resultSet = query.getResultList();
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
