package ru.andersen.travelagency.hibreq;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.andersen.travelagency.HibernateUtil;
import ru.andersen.travelagency.entity.User;

import java.util.List;
import java.util.Optional;

public class UserHib {

//create
    public static void save(User user) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.save(user);

        session.getTransaction().commit();

    }

    public static void update(User user) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.update(user);

        session.getTransaction().commit();

    }

    public static void delete(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        User user = session.get(User.class, id);
        session.delete(user);

        session.getTransaction().commit();

    }

    public static Optional<User> showById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        User user = session.get(User.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable(user);

    }

    public static List<User> show() {

        List <User> listOfUser = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        listOfUser = session.createQuery("from User").getResultList();

        session.getTransaction().commit();

        return listOfUser;

    }
}
