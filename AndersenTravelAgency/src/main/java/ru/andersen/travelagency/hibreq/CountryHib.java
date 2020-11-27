package ru.andersen.travelagency.hibreq;

import org.hibernate.Session;
import ru.andersen.travelagency.HibernateUtil;
import ru.andersen.travelagency.entity.Country;
import ru.andersen.travelagency.entity.User;

import java.util.List;
import java.util.Optional;

public class CountryHib {

    //create
    public static void save(Country country) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.save(country);

        session.getTransaction().commit();

    }

    public static void update(Country country) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.update(country);

        session.getTransaction().commit();

    }

    public static void delete(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Country country = session.get(Country.class, id);
        session.delete(country);

        session.getTransaction().commit();

    }

    public static Optional<Country> showById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Country country = session.get(Country.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable(country);

    }

    public static List<Country> show() {

        List <Country> listOfCountry = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        listOfCountry = session.createQuery("from Country").getResultList();

        session.getTransaction().commit();

        return listOfCountry;

    }
}
