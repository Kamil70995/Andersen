package ru.andersen.travelagency.dao;

import ru.andersen.travelagency.entity.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Kamil70995";

    private static final String INSERT_HOTELS_SQL = "INSERT INTO hotels (hotelname) VALUES " + " (?);";
    private static final String SELECT_HOTEL_BY_ID = "SELECT hotelname from hotels where hotel_id = ?;";
    private static final String SELECT_ALL_HOTELS = "SELECT * FROM hotels";
    private static final String DELETE_HOTELS_SQL = "DELETE from hotels where hotel_id = ?;";
    private static final String UPDATE_HOTELS_SQL = "UPDATE hotels set hotelname = ? where hotel_id = ?;";

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

    public void insertHotel(Hotel hotel) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_HOTELS_SQL);) {

            preparedStatement.setString(1, hotel.getHotelName());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //update user

    public boolean updateHotel(Hotel hotel) {

        boolean rowUpdated = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HOTELS_SQL);) {

            preparedStatement.setString(1, hotel.getHotelName());
            preparedStatement.setInt(2, hotel.getHotel_id());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdated;

    }

    //select user by id

    public Hotel selectHotel(int id) {

        Hotel hotel = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HOTEL_BY_ID);) {

            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String hotelName = rs.getString("hotelname");
                hotel = new Hotel(id, hotelName);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotel;
    }

    // select users

    public List<Hotel> selectAllUsers() {

        List<Hotel> hotels = new ArrayList<Hotel>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_HOTELS);) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String hotelName = rs.getString("hotelname");
                hotels.add(new Hotel(id, hotelName));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotels;
    }

    //delete user

    public boolean deleteHotel(int id) {
        boolean rowDeleted = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HOTELS_SQL);) {

            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rowDeleted;
    }
}
