package br.com.adbv.sgd.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class JpaUtils {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("SGDPU");
	}

	public static JpaUtils newInstance() {
		return new JpaUtils();
	}

	public EntityManager createEntityManager() {
		return getEntityManager();
	}

	public static EntityManager getEntityManager() {
		EntityManager entityManager = factory.createEntityManager();
		return entityManager;
	}

	public static Session getSession(EntityManager entityManager) {
		Session session = ((Session) entityManager.getDelegate());
		return session;
	}
}
