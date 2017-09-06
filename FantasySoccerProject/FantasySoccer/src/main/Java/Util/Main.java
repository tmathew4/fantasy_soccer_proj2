package Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import tables.Player;

public class Main {

    public static void main(String[] args) {

        Session s = SessionFactoryUtil.getSession();

        try{
            Player p = (Player)s.get(Player.class,1);
        }catch (HibernateException ex){
            ex.printStackTrace();
         }finally {
            s.close();
        }

    }
}
