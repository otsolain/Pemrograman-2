import java.sql.*;
import javax.swing.JOptionPane;

public class UpdateDataForm extends javax.swing.JFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;

    public UpdateDataForm() {
        initComponents();
        koneksi = db.getKoneksi();
    }

    private void updateData() {
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "UPDATE datanil SET nama=?, nil1=?, nil2=?, rata=? WHERE nim=?"
            );

            ps.setString(1, namaTF.getText());
            ps.setString(2, nil1TF.getText());
            ps.setString(3, nil2TF.getText());
            ps.setString(4, rataTF.getText());
            ps.setString(5, nimTF.getText());
            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Data berhasil diupdate!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data gagal diupdate!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Koneksi gagal " + e.toString());
        }
    }
}
