import java.sql.*;
import javax.swing.JOptionPane;

public class CariDataForm extends javax.swing.JFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;

    public CariDataForm() {
        initComponents();
        koneksi = db.getKoneksi();
    }

    private void cariDataSebagian() {
        try {
            Statement st = koneksi.createStatement();

            ResultSet rs = st.executeQuery(
                "Select * from datanil" +
                " where nama like('%" + namaTF.getText() + "%')"
            );

            if (rs.next()) {
                nimTF.setText(rs.getString("nim"));
                nil1TF.setText(rs.getString("nil1"));
                nil2TF.setText(rs.getString("nil2"));
                rataTF.setText(rs.getString("rata"));
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data tidak ada/Salah",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }

    private void cariDataLengkap() {
        try {
            Statement st = koneksi.createStatement();

            // SELECT dengan WHERE exact
            ResultSet rs = st.executeQuery(
                "Select * from datanil" +
                " where nim = '" + nimTF.getText() + "'"
            );

            if (rs.next()) {
                namaTF.setText(rs.getString("nama"));
                nil1TF.setText(rs.getString("nil1"));
                nil2TF.setText(rs.getString("nil2"));
                rataTF.setText(rs.getString("rata"));
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data tidak ditemukan",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }
}
