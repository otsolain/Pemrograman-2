package com.rentcar.model;

import java.sql.*;

public class Koneksi {
    private static final String driver   = "com.mysql.jdbc.Driver";
    private static final String database = "jdbc:mysql://localhost:3306/dbrentcar";
    private static final String user     = "root";
    private static final String password = "";
    private String pesanKesalahan;

    public String getPesanKesalahan() { return pesanKesalahan; }

    public Connection getConnection() {
        Connection connection = null;
        pesanKesalahan = "";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(database + "?user=" + user + "&password=" + password);
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "Driver tidak ditemukan: " + ex;
        } catch (SQLException ex) {
            pesanKesalahan = "Koneksi gagal: " + ex;
        }
        return connection;
    }
}
