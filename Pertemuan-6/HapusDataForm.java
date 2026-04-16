import java.sql.*;
import javax.swing.JOptionPane;

public class HapusDataForm extends javax.swing.JFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;

    public HapusDataForm() {
        initComponents();
        koneksi = db.getKoneksi();
    }

    private void hapusData() {
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "DELETE FROM datanil WHERE nim=?"
            );

            ps.setString(1, nimTF.getText());
            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Data berhasil dihapus!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data gagal dihapus!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }
}
