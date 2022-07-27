package com.academy.techcenture.ecommerce.utils;

import com.academy.techcenture.ecommerce.config.ConfigReader;

import java.sql.*;

public class DbUtils {

    private Connection connection;

    public DbUtils() throws SQLException {
        connection = DriverManager.getConnection(ConfigReader.getProperty("dbUrl"),
                ConfigReader.getProperty("dbUserName"), ConfigReader.getProperty("dbUserPassword"));
    }

    public ResultSet fetchDataFromDb(String query) throws Exception {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();//in order to see what went wrong

        }
        throw new Exception("Something went wrong");

    }

    public boolean deleteTable(String query) throws Exception {

        try {
            Statement statement = connection.createStatement();
            boolean resultSet = statement.execute(query);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();//in order to see what went wrong

        }
        throw new Exception("Something went wrong");

    }
    }



