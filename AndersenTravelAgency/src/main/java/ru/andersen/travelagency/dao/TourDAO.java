package ru.andersen.travelagency.dao;

import ru.andersen.travelagency.entity.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Kamil70995";

    private static final String INSERT_TOURS_SQL = "INSERT INTO tours (name, summary, description, departureDate, arrivalDate, price) VALUES " + " (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_TOUR_BY_ID = "SELECT tour_id, name, summary, description, departureDate, arrivalDate, price from tours where id = ?;";
    private static final String SELECT_ALL_TOURS = "SELECT * FROM tours";
    private static final String DELETE_TOURS_SQL = "DELETE from tours where tour_id = ?;";
    private static final String UPDATE_TOURS_SQL = "UPDATE tours set name = ?, summary = ?, description = ?, departureDate = ?, arrivalDate = ?, price = ? where tour_id =?;";


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
    // Create or insert tour

    public void insertTour(Tour tour) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TOURS_SQL);) {

            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getSummary());
            preparedStatement.setString(3, tour.getDescription());
            preparedStatement.setDate(4, (Date) tour.getDepartureDate());
            preparedStatement.setDate(5, (Date) tour.getDepartureDate());
            preparedStatement.setDouble(6, tour.getPrice());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //update user

    public boolean updateTour(Tour tour) {

        boolean rowUpdated = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOURS_SQL);) {

            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getSummary());
            preparedStatement.setString(3, tour.getDescription());
            preparedStatement.setDate(4, (Date) tour.getDepartureDate());
            preparedStatement.setDate(5, (Date) tour.getDepartureDate());
            preparedStatement.setDouble(6, tour.getPrice());
            preparedStatement.setLong(7, tour.getTour_id());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdated;

    }

    //select user by id

    public Tour selectTour(long id) {

       Tour tour = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOUR_BY_ID);) {

            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                String summary = rs.getString("summary");
                String description = rs.getString("descrption");
                Date departureDate = rs.getDate("departureDate");
                Date arrivalDate = rs.getDate("arrivalDate");
                double price = rs.getDouble("price");
                tour = new Tour(id, name, summary, description, departureDate, arrivalDate, price);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tour;
    }

    // select users

    public List<Tour> selectAllTours() {

        List<Tour> tours = new ArrayList<Tour>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TOURS);) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String summary = rs.getString("summary");
                String description = rs.getString("descrption");
                Date departureDate = rs.getDate("departureDate");
                Date arrivalDate = rs.getDate("arrivalDate");
                double price = rs.getDouble("price");
                tours.add(new Tour(id, name, summary, description, departureDate, arrivalDate, price));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tours;
    }

    //delete user

    public boolean deleteTour(long id) {
        boolean rowDeleted = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TOURS_SQL);) {

            preparedStatement.setLong(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rowDeleted;
    }
}
