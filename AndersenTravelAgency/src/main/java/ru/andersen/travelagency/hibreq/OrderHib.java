package ru.andersen.travelagency.hibreq;

import org.apache.xpath.operations.Or;
import org.hibernate.Session;
import ru.andersen.travelagency.HibernateUtil;
import ru.andersen.travelagency.entity.Order;
import ru.andersen.travelagency.entity.User;

import java.util.List;
import java.util.Optional;

public class OrderHib {

    //create
    public static void save(Order order) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.save(order);

        session.getTransaction().commit();

    }

    public static void update(Order order) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.update(order);

        session.getTransaction().commit();

    }

    public static void delete(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Order order = session.get(Order.class, id);
        session.delete(order);

        session.getTransaction().commit();

    }

    public static Optional<Order> showById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Order order = session.get(Order.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable(order);

    }


    public static List<Order> show() {

        List <Order> listOfOrder = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        listOfOrder = session.createQuery("from Order").getResultList();

        session.getTransaction().commit();

        return listOfOrder;

    }
}
