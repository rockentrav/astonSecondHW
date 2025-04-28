package org.example.util;

import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateFactoryUtil {
    private static SessionFactory sessionFactory;

    static {
        buildSessionFactory();
    }

    private static void buildSessionFactory() {
        try {
            Properties props = new Properties();
            props.load(HibernateFactoryUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));

            // Переопределяем из System properties, если заданы
            String jdbcUrl = System.getProperty("hibernate.connection.url");
            String username = System.getProperty("hibernate.connection.username");
            String password = System.getProperty("hibernate.connection.password");

            if (jdbcUrl != null) props.setProperty("hibernate.connection.url", jdbcUrl);
            if (username != null) props.setProperty("hibernate.connection.username", username);
            if (password != null) props.setProperty("hibernate.connection.password", password);

            Configuration configuration = new Configuration();
            configuration.setProperties(props);
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}