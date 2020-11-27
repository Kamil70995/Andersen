package ru.andersen.travelagency.hibreq;

import org.hibernate.Session;
import ru.andersen.travelagency.HibernateUtil;
import ru.andersen.travelagency.entity.Tour;
import ru.andersen.travelagency.entity.User;

import java.util.List;
import java.util.Optional;

public class TourHib {

    //create
    public static void save(Tour tour) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.save(tour);

        session.getTransaction().commit();

    }

    public static void update(Tour tour) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.update(tour);

        session.getTransaction().commit();

    }

    public static void delete(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Tour tour = session.get(Tour.class, id);
        session.delete(tour);

        session.getTransaction().commit();

    }

    public static Optional<Tour> showById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Tour tour = session.get(Tour.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable(tour);

    }

    public static List<Tour> show() {

        List <Tour> listOfTour = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        listOfTour = session.createQuery("from Tour").getResultList();

        session.getTransaction().commit();

        return listOfTour;

    }
}
