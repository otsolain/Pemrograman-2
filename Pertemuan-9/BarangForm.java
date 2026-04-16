import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BarangForm extends JInternalFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;
    DefaultTableModel modelTabel;
    JTextField kdTF, nmTF, hargaTF, stokTF;
    JTable tabel;

    public BarangForm() {
        setTitle("Data Barang");
        setSize(700, 450);
        setClosable(true); setMaximizable(true); setIconifiable(true);
        koneksi = db.getKoneksi();
        initComponents();
        tampilData();
    }

    private void initComponents() {
        kdTF    = new JTextField(10);
        nmTF    = new JTextField(20);
        hargaTF = new JTextField(10);
        stokTF  = new JTextField(5);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnHapus  = new JButton("Hapus");
        JButton btnBersih = new JButton("Bersih");

        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e  -> hapusData());
        btnBersih.addActionListener(e -> bersihField());

        JPanel panelInput = new JPanel();
        panelInput.add(new JLabel("Kode:")); panelInput.add(kdTF);
        panelInput.add(new JLabel("Nama:")); panelInput.add(nmTF);
        panelInput.add(new JLabel("Harga:")); panelInput.add(hargaTF);
        panelInput.add(new JLabel("Stok:")); panelInput.add(stokTF);
        panelInput.add(btnSimpan); panelInput.add(btnHapus); panelInput.add(btnBersih);

        modelTabel = new DefaultTableModel();
        tabel = new JTable(modelTabel);
        modelTabel.addColumn("Kode");
        modelTabel.addColumn("Nama Barang");
        modelTabel.addColumn("Harga");
        modelTabel.addColumn("Stok");

        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tabel.getSelectedRow();
                kdTF.setText(modelTabel.getValueAt(row, 0).toString());
                nmTF.setText(modelTabel.getValueAt(row, 1).toString());
                hargaTF.setText(modelTabel.getValueAt(row, 2).toString());
                stokTF.setText(modelTabel.getValueAt(row, 3).toString());
            }
        });

        setLayout(new java.awt.BorderLayout());
        add(panelInput, java.awt.BorderLayout.NORTH);
        add(new JScrollPane(tabel), java.awt.BorderLayout.CENTER);
    }

    private void simpanData() {
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "INSERT INTO barang (kd_barang, nm_barang, harga, stok) VALUES (?,?,?,?)"
            );
            ps.setString(1, kdTF.getText());
            ps.setString(2, nmTF.getText());
            ps.setDouble(3, Double.parseDouble(hargaTF.getText()));
            ps.setInt(4, Integer.parseInt(stokTF.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            bersihField(); tampilData();
        } catch (SQLException e) {
            System.out.println("Gagal simpan: " + e.toString());
        }
    }

    private void hapusData() {
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "DELETE FROM barang WHERE kd_barang=?"
            );
            ps.setString(1, kdTF.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            bersihField(); tampilData();
        } catch (SQLException e) {
            System.out.println("Gagal hapus: " + e.toString());
        }
    }

    private void tampilData() {
        try {
            while (modelTabel.getRowCount() > 0) modelTabel.removeRow(0);
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM barang ORDER BY kd_barang ASC");
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                    rs.getString("kd_barang"),
                    rs.getString("nm_barang"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                });
            }
        } catch (SQLException e) {
            System.out.println("Gagal tampil: " + e.toString());
        }
    }

    private void bersihField() {
        kdTF.setText(""); nmTF.setText("");
        hargaTF.setText(""); stokTF.setText("");
    }
}
