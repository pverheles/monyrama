package com.monyrama.controller;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;

/**
 * Implements utility methods to work with
 * Hibernate session
 *
 * @author Petro_Verheles
 *
 */
public class HibernateUtil {

	private static final String JDBC_H2_FILE = "jdbc:h2:file:";
	private static final String DB_NAME = "Monyrama";
	/** PSetting.value maps to a column named VALUE, which is a reserved word in H2 2.x */
	private static final String JDBC_H2_OPTIONS = ";NON_KEYWORDS=VALUE";

	//Single object of SessionFactory
    private static SessionFactory sessionFactory = null;
	private static String dataBaseFolderPath;

    public static void configureApp() {
        try {
        	Configuration configuration = new Configuration();
        	configuration.configure();
        	dataBaseFolderPath = MyPreferences.getString(PrefKeys.DATAFOLDER_PATH, System.getProperty("user.home") + File.separator + DB_NAME);
        	File dataFolder = new File(dataBaseFolderPath);
        	configuration.setProperty("hibernate.connection.url", JDBC_H2_FILE + dataFolder.getAbsolutePath() + File.separator + DB_NAME + JDBC_H2_OPTIONS);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Gets the single object of SessionFactory
     *
     * @return - session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void doInTransaction(Executable executable) {
    	Session session = sessionFactory.openSession();
    	try {
    		session.beginTransaction();
    		executable.execute(session);
    		session.getTransaction().commit();
    	} catch (RuntimeException e) {
    		rollback(session);
    		throw e;
    	} finally {
    		session.close();
    	}
    }

    public static <T> T queryInTransaction(Resultable<T> queriable) {
    	Session session = sessionFactory.openSession();
    	try {
    		session.beginTransaction();
    		T t = queriable.getResult(session);
    		session.getTransaction().commit();
    		return t;
    	} catch (RuntimeException e) {
    		rollback(session);
    		throw e;
    	} finally {
    		session.close();
    	}
    }

    /**
     * Inserts or updates the given transient or detached entity.
     * Replaces Session.saveOrUpdate/save/update, which Hibernate 7 removed. Entity ids are
     * assigned by the application (see BaseEntity.prepareToPersist), so merge() decides
     * between INSERT and UPDATE by looking the id up. The passed object stays detached.
     */
    public static void saveOrUpdate(Session session, Object entity) {
    	session.merge(entity);
    }

    /**
     * Deletes the given entity, which may be detached.
     * Replaces Session.delete, which Hibernate 7 removed (remove() only accepts managed entities).
     */
    public static void delete(Session session, Object entity) {
    	session.remove(session.contains(entity) ? entity : session.merge(entity));
    }

    public static String getDatabaseFolderPath() {
    	return dataBaseFolderPath;
    }

	private static void rollback(Session session) {
		try {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
		} catch (RuntimeException ignored) {
			// keep the original exception
		}
	}
}
