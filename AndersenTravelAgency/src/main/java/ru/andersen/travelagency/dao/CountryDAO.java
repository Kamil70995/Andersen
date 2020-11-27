package ru.andersen.travelagency.dao;

import ru.andersen.travelagency.entity.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Kamil70995";


    private static final String INSERT_COUNTRIES_SQL = "INSERT INTO countries (nameCountry) VALUES " + " (?);";
    private static final String SELECT_COUNTRY_BY_ID = "SELECT nameCountry from countries where country_id = ?;";
    private static final String SELECT_ALL_COUNTRIES = "SELECT * FROM countries";
    private static final String DELETE_COUNTRIES_SQL = "DELETE from countries where country_id = ?;";
    private static final String UPDATE_COUNTRIES_SQL = "UPDATE countries set nameCountry = ? where country_id =?;";


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

    public void insertCountry(Country country) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COUNTRIES_SQL);) {

            preparedStatement.setString(1, country.getNameCountry());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //update user

    public boolean updateCountry(Country country) {

        boolean rowUpdated = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COUNTRIES_SQL);) {

            preparedStatement.setString(1, country.getNameCountry());
            preparedStatement.setLong(2, country.getCountry_id());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdated;

    }

    //select user by id

    public Country selectCountry(long id) {

        Country country = null;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNTRY_BY_ID);) {

            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                String nameCountry = rs.getString("nameCountry");

                country = new Country(id, nameCountry);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }

    // select users

    public List<Country> selectAllCountries() {

        List<Country> countries = new ArrayList<Country>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COUNTRIES);) {

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String nameCountry = rs.getString("nameCountry");
                countries.add(new Country(id, nameCountry));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }

    //delete user

    public boolean deleteCountry(long id) {
        boolean rowDeleted = true;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COUNTRIES_SQL);) {

            preparedStatement.setLong(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rowDeleted;
    }
}
