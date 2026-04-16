import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseKoneksi {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_nilai";
    private static final String USER = "root";
    private static final String PASS = "";

    public Connection getKoneksi() {
        Connection koneksi = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            koneksi = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi berhasil!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal: " + e.toString());
        }
        return koneksi;
    }
}
