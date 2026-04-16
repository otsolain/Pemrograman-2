import java.sql.*;

public class DatabaseKoneksi {
    public Connection getKoneksi() {
        Connection koneksi = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_penjualan?user=root&password="
            );
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.toString());
        }
        return koneksi;
    }
}
