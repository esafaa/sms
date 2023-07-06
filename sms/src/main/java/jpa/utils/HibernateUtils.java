package jpa.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/* To make sure we are using only one SessionFactory during our Program*/
public class HibernateUtils {

	private static SessionFactory sessionFactory = null;

	public static SessionFactory getSessionFactory() {
		try {
		if (sessionFactory == null) {
		
			sessionFactory = new Configuration().configure().buildSessionFactory();

		}
		return sessionFactory;
		}catch(Exception e) {
			System.out.println("Oops, Couldn't creat SessionFactory");
			return null;
		}
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			try {
				sessionFactory.close();// sessionFactory= null so we can create new SessionFactory if needed

			} catch (Exception e) {
				System.out.println("Cannot close the session");
			}
		}
	}
}
