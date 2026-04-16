import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseKoneksi {

    public Connection getKoneksi() {
        Connection koneksi = null;
        try {
            // Langkah 1: Load/panggil JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Langkah 2: Buat koneksi ke database
            String url = "jdbc:mysql://localhost:3306/MHS?user=root&password=";
            koneksi = DriverManager.getConnection(url);

            System.out.println("Koneksi berhasil!");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal: " + e.toString());
        }
        return koneksi;
    }
}
