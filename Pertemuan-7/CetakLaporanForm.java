import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class CetakLaporanForm extends javax.swing.JFrame {

    public CetakLaporanForm() {
        setTitle("Cetak Laporan");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JButton btnCetak = new JButton("Cetak Laporan");
        btnCetak.addActionListener(e -> cetakLaporan());

        setLayout(new java.awt.FlowLayout());
        add(btnCetak);
    }

    private void cetakLaporan() {
        // Kode koneksi database (dari slide)
        String user = "root";
        String pass = "";
        String host = "localhost";
        String db   = "datamhs";
        String url  = "";

        // Mendapatkan direktori project
        File dir1 = new File(".");
        String dirr = "";

        try {
            // Ambil path direktori project
            dirr = dir1.getCanonicalPath();

            // Buat URL koneksi
            url = "jdbc:mysql://" + host + "/" + db +
                  "?user=" + user + "&password=" + pass;

            // Buat koneksi ke database
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection(url);

            // Path ke file laporan (.jasper hasil compile dari .jrxml)
            String reportPath = dirr + "/src/laporan/laporanMahasiswa.jasper";

            // Parameter tambahan untuk laporan (opsional)
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("judul", "Laporan Data Mahasiswa");

            // Isi laporan dengan data dari database
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                reportPath,
                parameter,
                koneksi
            );

            // Tampilkan laporan di JasperViewer
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal cetak laporan: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: " + e.toString());
        }
    }

    public static void main(String[] args) {
        new CetakLaporanForm().setVisible(true);
    }
}
