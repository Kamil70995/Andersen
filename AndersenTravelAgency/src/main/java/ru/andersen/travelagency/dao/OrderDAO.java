package ru.andersen.travelagency.dao;

import ru.andersen.travelagency.entity.Order;
import ru.andersen.travelagency.entity.Tour;
import ru.andersen.travelagency.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Kamil70995";

    private static final String INSERT_ORDERS_SQL = "INSERT INTO orders (user_id, id_tour, quantity, total_price, order_date, departure_date, arrival_date) VALUES " + " (?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_ORDER_BY_ID = "SELECT user_id, tour_id, quantity, total_price, order_date, departure_date, arrival_date from orders where order_id = ?;";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String DELETE_ORDERS_SQL = "DELETE from orders where order_id = ?;";
    private static final String UPDATE_ORDERS_SQL = "UPDATE orders set user_id = ?, tour_id = ?, quantity = ?, total_price = ?, order_date = ?, departure_date = ?, arrival_date = ? where order_id =?;";


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

    public void insertOrder(Order order) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDERS_SQL);) {

            preparedStatement.setObject(1, order.getUser_id());
            preparedStatement.setObject(2, order.getTour_id());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setDate(5, (Date) order.getOrderDate());
            preparedStatement.setDate(6, (Date) order.getDepartureDate());
            preparedStatement.setDate(7, (Date) order.getArrivalDate());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //update user

    public boolean updateOrder(Order order) {

        boolean rowUpdated = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDERS_SQL);) {

            preparedStatement.setObject(1, order.getUser_id());
            preparedStatement.setObject(2, order.getTour_id());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setDate(5, (Date) order.getOrderDate());
            preparedStatement.setDate(6, (Date) order.getDepartureDate());
            preparedStatement.setDate(7, (Date) order.getArrivalDate());
            preparedStatement.setLong(8, order.getOrderId());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdated;

    }

    //select user by id

    public Order selectOrder(Long id) {

        Order order = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID);) {

            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                User user_id = (User) rs.getObject("user_id");
                Tour tour_id = (Tour) rs.getObject("tour_id");
                int quantity = rs.getInt("quantity");
                double total_price = rs.getDouble("total_price");
                Date order_date = rs.getDate("order_date");
                Date departure_date = rs.getDate("departure_date");
                Date arrival_date = rs.getDate("arrival_date");
                order = new Order(id, user_id, tour_id, quantity, total_price, order_date, departure_date, arrival_date);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return order;
    }

    // select users

    public List<Order> selectAllOrders() {

        List<Order> orders = new ArrayList<Order>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Long id = rs.getLong("order_id");
                User user_id = (User) rs.getObject("user_id");
                Tour tour_id = (Tour) rs.getObject("tour_id");
                int quantity = rs.getInt("quantity");
                double total_price = rs.getDouble("total_price");
                Date order_date = rs.getDate("order_date");
                Date departure_date = rs.getDate("departure_date");
                Date arrival_date = rs.getDate("arrival_date");
                orders.add(new Order(id, user_id, tour_id, quantity, total_price, order_date, departure_date, arrival_date));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    //delete user

    public boolean deleteOrder(Long id) {
        boolean rowDeleted = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDERS_SQL);) {

            preparedStatement.setLong(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rowDeleted;
    }
}
