import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JInternalFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;
    DefaultTableModel modelTabel;
    JTextField kdTF, nmTF, alamatTF, telpTF;
    JTable tabel;

    public CustomerForm() {
        setTitle("Data Customer");
        setSize(700, 450);
        setClosable(true); setMaximizable(true); setIconifiable(true);
        koneksi = db.getKoneksi();
        initComponents();
        tampilData();
    }

    private void initComponents() {
        kdTF     = new JTextField(10);
        nmTF     = new JTextField(20);
        alamatTF = new JTextField(25);
        telpTF   = new JTextField(15);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnHapus  = new JButton("Hapus");
        JButton btnBersih = new JButton("Bersih");

        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e  -> hapusData());
        btnBersih.addActionListener(e -> bersihField());

        JPanel panelInput = new JPanel();
        panelInput.add(new JLabel("Kode:"));    panelInput.add(kdTF);
        panelInput.add(new JLabel("Nama:"));    panelInput.add(nmTF);
        panelInput.add(new JLabel("Alamat:"));  panelInput.add(alamatTF);
        panelInput.add(new JLabel("Telp:"));    panelInput.add(telpTF);
        panelInput.add(btnSimpan); panelInput.add(btnHapus); panelInput.add(btnBersih);

        modelTabel = new DefaultTableModel();
        tabel = new JTable(modelTabel);
        modelTabel.addColumn("Kode");
        modelTabel.addColumn("Nama Customer");
        modelTabel.addColumn("Alamat");
        modelTabel.addColumn("Telp");

        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tabel.getSelectedRow();
                kdTF.setText(modelTabel.getValueAt(row, 0).toString());
                nmTF.setText(modelTabel.getValueAt(row, 1).toString());
                alamatTF.setText(modelTabel.getValueAt(row, 2).toString());
                telpTF.setText(modelTabel.getValueAt(row, 3).toString());
            }
        });

        setLayout(new java.awt.BorderLayout());
        add(panelInput, java.awt.BorderLayout.NORTH);
        add(new JScrollPane(tabel), java.awt.BorderLayout.CENTER);
    }

    private void simpanData() {
        try {
            PreparedStatement ps = koneksi.prepareStatement(
                "INSERT INTO customer (kd_customer, nm_customer, alamat, telp) VALUES (?,?,?,?)"
            );
            ps.setString(1, kdTF.getText());
            ps.setString(2, nmTF.getText());
            ps.setString(3, alamatTF.getText());
            ps.setString(4, telpTF.getText());
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
                "DELETE FROM customer WHERE kd_customer=?"
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
            ResultSet rs = st.executeQuery("SELECT * FROM customer ORDER BY kd_customer ASC");
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                    rs.getString("kd_customer"),
                    rs.getString("nm_customer"),
                    rs.getString("alamat"),
                    rs.getString("telp")
                });
            }
        } catch (SQLException e) {
            System.out.println("Gagal tampil: " + e.toString());
        }
    }

    private void bersihField() {
        kdTF.setText(""); nmTF.setText("");
        alamatTF.setText(""); telpTF.setText("");
    }
}
