package com.ex.FantasySoccerLeague.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.ex.FantasySoccerLeague.tables.Player;

public class Driver {

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
