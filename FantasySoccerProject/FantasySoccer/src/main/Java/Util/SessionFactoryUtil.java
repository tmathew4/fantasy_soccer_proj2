package Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private static SessionFactory sf;

    static {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();

        sf = config.buildSessionFactory(serviceRegistry);
    }

    public static Session getSession(){
        return sf.openSession();
    }
}
