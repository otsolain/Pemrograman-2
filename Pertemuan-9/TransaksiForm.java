import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class TransaksiForm extends JInternalFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;
    DefaultTableModel modelDetail;
    JTextField noTF, kdCustomerTF, kdBarangTF, qtyTF, totalTF;
    JTable tabelDetail;

    public TransaksiForm() {
        setTitle("Transaksi Penjualan");
        setSize(750, 500);
        setClosable(true); setMaximizable(true); setIconifiable(true);
        koneksi = db.getKoneksi();
        initComponents();
    }

    private void initComponents() {
        noTF         = new JTextField(12);
        kdCustomerTF = new JTextField(10);
        kdBarangTF   = new JTextField(10);
        qtyTF        = new JTextField(5);
        totalTF      = new JTextField(12);
        totalTF.setEditable(false);

        JButton btnTambahItem = new JButton("Tambah Item");
        JButton btnSimpan     = new JButton("Simpan Transaksi");

        btnTambahItem.addActionListener(e -> tambahItem());
        btnSimpan.addActionListener(e     -> simpanTransaksi());

        JPanel panelHeader = new JPanel();
        panelHeader.add(new JLabel("No Transaksi:")); panelHeader.add(noTF);
        panelHeader.add(new JLabel("Kode Customer:")); panelHeader.add(kdCustomerTF);

        JPanel panelItem = new JPanel();
        panelItem.add(new JLabel("Kode Barang:")); panelItem.add(kdBarangTF);
        panelItem.add(new JLabel("Qty:")); panelItem.add(qtyTF);
        panelItem.add(btnTambahItem);

        JPanel panelTotal = new JPanel();
        panelTotal.add(new JLabel("Total:")); panelTotal.add(totalTF);
        panelTotal.add(btnSimpan);

        modelDetail = new DefaultTableModel();
        tabelDetail = new JTable(modelDetail);
        modelDetail.addColumn("Kode Barang");
        modelDetail.addColumn("Nama Barang");
        modelDetail.addColumn("Harga");
        modelDetail.addColumn("Qty");
        modelDetail.addColumn("Subtotal");

        JPanel panelNorth = new JPanel(new java.awt.GridLayout(3, 1));
        panelNorth.add(panelHeader);
        panelNorth.add(panelItem);
        panelNorth.add(panelTotal);

        setLayout(new java.awt.BorderLayout());
        add(panelNorth, java.awt.BorderLayout.NORTH);
        add(new JScrollPane(tabelDetail), java.awt.BorderLayout.CENTER);
    }

    private void tambahItem() {
        try {
            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM barang WHERE kd_barang='" + kdBarangTF.getText() + "'"
            );
            if (rs.next()) {
                double harga   = rs.getDouble("harga");
                int qty        = Integer.parseInt(qtyTF.getText());
                double subtotal = harga * qty;

                modelDetail.addRow(new Object[]{
                    rs.getString("kd_barang"),
                    rs.getString("nm_barang"),
                    harga, qty, subtotal
                });

                // Update total
                double total = 0;
                for (int i = 0; i < modelDetail.getRowCount(); i++) {
                    total += (double) modelDetail.getValueAt(i, 4);
                }
                totalTF.setText(String.valueOf(total));
                kdBarangTF.setText(""); qtyTF.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    private void simpanTransaksi() {
        try {
            // Simpan header transaksi
            PreparedStatement psHeader = koneksi.prepareStatement(
                "INSERT INTO transaksi (no_transaksi, kd_customer, tgl_transaksi, total) VALUES (?,?,?,?)"
            );
            psHeader.setString(1, noTF.getText());
            psHeader.setString(2, kdCustomerTF.getText());
            psHeader.setDate(3, new java.sql.Date(new Date().getTime()));
            psHeader.setDouble(4, Double.parseDouble(totalTF.getText()));
            psHeader.executeUpdate();

            // Simpan detail transaksi
            for (int i = 0; i < modelDetail.getRowCount(); i++) {
                PreparedStatement psDetail = koneksi.prepareStatement(
                    "INSERT INTO detail_transaksi (no_transaksi, kd_barang, qty, subtotal) VALUES (?,?,?,?)"
                );
                psDetail.setString(1, noTF.getText());
                psDetail.setString(2, modelDetail.getValueAt(i, 0).toString());
                psDetail.setInt(3, Integer.parseInt(modelDetail.getValueAt(i, 3).toString()));
                psDetail.setDouble(4, Double.parseDouble(modelDetail.getValueAt(i, 4).toString()));
                psDetail.executeUpdate();

                // Update stok barang
                PreparedStatement psStok = koneksi.prepareStatement(
                    "UPDATE barang SET stok = stok - ? WHERE kd_barang=?"
                );
                psStok.setInt(1, Integer.parseInt(modelDetail.getValueAt(i, 3).toString()));
                psStok.setString(2, modelDetail.getValueAt(i, 0).toString());
                psStok.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!");
            while (modelDetail.getRowCount() > 0) modelDetail.removeRow(0);
            noTF.setText(""); kdCustomerTF.setText(""); totalTF.setText("");

        } catch (SQLException e) {
            System.out.println("Gagal simpan transaksi: " + e.toString());
        }
    }
}
