package ru.andersen.travelagency.hibreq;

import org.hibernate.Session;
import ru.andersen.travelagency.HibernateUtil;
import ru.andersen.travelagency.entity.Hotel;
import ru.andersen.travelagency.entity.User;

import java.util.List;
import java.util.Optional;

public class HotelHib {
    //create
    public static void save(Hotel hotel) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.save(hotel);

        session.getTransaction().commit();

    }

    public static void update(Hotel hotel) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.update(hotel);

        session.getTransaction().commit();

    }

    public static void delete(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Hotel hotel = session.get(Hotel.class, id);
        session.delete(hotel);

        session.getTransaction().commit();

    }

    public static Optional<Hotel> showById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Hotel hotel = session.get(Hotel.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable(hotel);

    }

    public static List<Hotel> show() {

        List <Hotel> listOfHotel = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        listOfHotel = session.createQuery("from Hotel").getResultList();

        session.getTransaction().commit();

        return listOfHotel;

    }

}
