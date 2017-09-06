package Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private static SessionFactory sf;

    static{
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        StandardServiceRegist
    }
}
