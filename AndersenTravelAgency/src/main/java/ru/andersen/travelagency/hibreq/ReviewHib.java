package ru.andersen.travelagency.hibreq;

import org.hibernate.Session;
import ru.andersen.travelagency.HibernateUtil;
import ru.andersen.travelagency.entity.Review;
import ru.andersen.travelagency.entity.User;

import java.util.List;
import java.util.Optional;

public class ReviewHib {

    //create
    public static void save(Review review) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.save(review);

        session.getTransaction().commit();

    }

    public static void update(Review review) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        session.update(review);

        session.getTransaction().commit();

    }

    public static void delete(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Review review = session.get(Review.class, id);
        session.delete(review);

        session.getTransaction().commit();

    }

    public static Optional<Review> showById(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        Review review = session.get(Review.class, id);

        session.getTransaction().commit();
        return Optional.ofNullable(review);

    }

    public static List<Review> show() {

        List <Review> listOfReview = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        listOfReview = session.createQuery("from Review").getResultList();

        session.getTransaction().commit();

        return listOfReview;

    }
}
