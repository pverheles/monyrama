package com.monyrama.controller;

import java.io.File;

import com.monyrama.preferences.MyPreferences;
import com.monyrama.controller.Executable;
import com.monyrama.controller.Resultable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
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

	//Single object of SessionFactory
    private static SessionFactory sessionFactory = null;
	private static Configuration configuration;
	private static Session session;
	private static String dataBaseFolderPath;

    public static void configureApp() {
        try {
        	configuration = new AnnotationConfiguration();
        	configuration.configure();
        	dataBaseFolderPath = MyPreferences.getString(PrefKeys.DATAFOLDER_PATH, System.getProperty("user.home") + File.separator + DB_NAME);
        	File dataFolder = new File(dataBaseFolderPath);
        	configuration.setProperty("hibernate.connection.url", JDBC_H2_FILE + dataFolder.getAbsolutePath() + File.separator + DB_NAME);
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
    	Session session = getSession();
    	session.beginTransaction();
    	executable.execute(session);
    	session.getTransaction().commit();
    	session.close();
    }
    
    public static <T> T queryInTransaction(Resultable<T> queriable) {
    	Session session = getSession();
    	session.beginTransaction();
    	T t = queriable.getResult(session);
    	session.getTransaction().commit();
    	session.close();
    	return t;
    }
    
    public static String getDatabaseFolderPath() {
    	return dataBaseFolderPath;
    }
    
	private static Session getSession() {
		session = sessionFactory.openSession();			
		return session;
	}
}
