package com.san.config;

import java.util.Properties;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.reflections.Reflections;

public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				configuration.setProperties(hibernateProperties());

				registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

				// Add Annotated Entity classes
				addEntityClassesByPackage(configuration, "com.san.entity");
				sessionFactory = configuration.buildSessionFactory(registry);

				// MetadataSources metadataSources = new MetadataSources(registry);
				// addEntityClassesByPackage(metadataSources, "com.san.entity");
				// sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	public static Session openSession() {
		return getSessionFactory().openSession();
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	private static Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();

		// H2 Database configuration
		// hibernateProperties.put(Environment.DRIVER, "org.h2.Driver");
		// hibernateProperties.put(Environment.URL, "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		// hibernateProperties.put(Environment.USER, "sa");
		// hibernateProperties.put(Environment.PASS, "sa");
		// hibernateProperties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");

		// Postgres Database configuration
		hibernateProperties.put(Environment.DRIVER, "org.postgresql.Driver");
		hibernateProperties.put(Environment.URL, "jdbc:postgresql://localhost:5432/test");
		hibernateProperties.put(Environment.USER, "postgres");
		hibernateProperties.put(Environment.PASS, "postgres");
		hibernateProperties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

		hibernateProperties.put(Environment.SHOW_SQL, "true");
		hibernateProperties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
		hibernateProperties.put(Environment.HBM2DDL_AUTO, "update");
		hibernateProperties.put(Environment.AUTOCOMMIT, "true");
		return hibernateProperties;
	}

	private static void addEntityClassesByPackage(Configuration configuration, String pkg) {
		Reflections reflections = new Reflections(pkg);
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
		for (Class<?> clazz : classes) {
			configuration.addAnnotatedClass(clazz);
		}
	}

	/*
	 * private static void addEntityClassesByPackage(MetadataSources metadataSources, String pkg) { Reflections reflections = new Reflections(pkg); Set<Class<?>> classes = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class); for
	 * (Class<?> clazz : classes) { metadataSources.addAnnotatedClass(clazz); } }
	 */

}
