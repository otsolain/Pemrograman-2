import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TampilDataForm extends javax.swing.JFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;
    DefaultTableModel modelTabel;
    JTable tabel;

    public TampilDataForm() {
        koneksi = db.getKoneksi();
        setTitle("Tampil Data Mahasiswa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initTabel();
        tampilkanData();
    }

    private void initTabel() {
        modelTabel = new DefaultTableModel();
        tabel = new JTable(modelTabel);

        modelTabel.addColumn("NIM");
        modelTabel.addColumn("Nama");
        modelTabel.addColumn("Semester");
        modelTabel.addColumn("Kelas");

        add(new JScrollPane(tabel));
    }

    private void tampilkanData() {
        try {
            // Langkah 3: buat dan kirim perintah SQL
            Statement st = koneksi.createStatement();

            // Berbagai variasi SELECT sesuai slide
            // ResultSet rs = st.executeQuery("SELECT * FROM datamhs");
            // ResultSet rs = st.executeQuery("SELECT nim, nama FROM datamhs");
            ResultSet rs = st.executeQuery(
                "SELECT * FROM datamhs ORDER BY nama ASC"
            );

            // Langkah 4: eksekusi & tampilkan data
            while (rs.next()) {
                Object[] baris = {
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("semester"),
                    rs.getString("kelas")
                };
                modelTabel.addRow(baris);
            }

        } catch (SQLException e) {
            System.out.println("Gagal tampil data: " + e.toString());
        }
    }

    public static void main(String[] args) {
        new TampilDataForm().setVisible(true);
    }
}
