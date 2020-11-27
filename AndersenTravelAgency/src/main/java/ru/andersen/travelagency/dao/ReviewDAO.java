package ru.andersen.travelagency.dao;

import ru.andersen.travelagency.entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Kamil70995";

    private static final String INSERT_REVIEWS_SQL = "INSERT INTO reviews (desctipton, like) VALUES " + " (?, ?);";
    private static final String SELECT_REVIEW_BY_ID = "SELECT review_id, descripton, isLike from reviews where review_id = ?;";
    private static final String SELECT_ALL_REVIEWS = "SELECT * FROM reviews";
    private static final String DELETE_REVIEWS_SQL = "DELETE from reviews where review_id = ?;";
    private static final String UPDATE_REVIEWS_SQL = "UPDATE reviews set description = ?, like = ? where review_id =?;";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    // Create or insert user

    public void insertReviews(Review review) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REVIEWS_SQL);) {

            preparedStatement.setString(1, review.getDescription());
            preparedStatement.setBoolean(2, review.isLike());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //update user

    public boolean updateReviews(Review review) {

        boolean rowUpdated = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REVIEWS_SQL);) {

            preparedStatement.setString(1, review.getDescription());
            preparedStatement.setBoolean(2, review.isLike());
            preparedStatement.setLong(3, review.getReview_id());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdated;

    }

    //select user by id

    public Review selectReview(int id) {

        Review review = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REVIEW_BY_ID);) {

            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String description = rs.getString("description");
                boolean isLike = rs.getBoolean("like");
                review = new Review(id, description, isLike);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return review;
    }

    // select users

    public List<Review> selectAllReviews() {

        List<Review> reviews = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_REVIEWS);) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                boolean isLike = rs.getBoolean("like");
                reviews.add(new Review(id, description, isLike));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reviews;
    }

    //delete user

    public boolean deleteReview(int id) {
        boolean rowDeleted = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REVIEWS_SQL);) {

            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rowDeleted;
    }


}



