package com.softtechbootcamp.bitirme.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {

    public static Connection getMySQLConnection()
            throws  SQLException {
        String hostName = "localhost";
        String dbName = "bitirmedb";
        String userName = "root";
        // Your database server passport
        String password = "password";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException {

        // if database server in different port than change 3306 to your port.
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?UseUnicode=true&characterEncoding=UTF-8&charSet=UTF-8&serverTimezone=Europe/Istanbul";

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}
