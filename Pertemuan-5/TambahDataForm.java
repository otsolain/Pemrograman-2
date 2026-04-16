import java.sql.*;
import javax.swing.*;

public class TambahDataForm extends javax.swing.JFrame {

    DatabaseKoneksi db = new DatabaseKoneksi();
    Connection koneksi;

    // Komponen GUI (dibuat manual / via NetBeans)
    JTextField nimTF, namaTF, semesterTF, kelasTF;
    JButton btnSimpan;

    public TambahDataForm() {
        koneksi = db.getKoneksi();
        initComponents();
    }

    private void initComponents() {
        setTitle("Tambah Data Mahasiswa");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        nimTF      = new JTextField();
        namaTF     = new JTextField();
        semesterTF = new JTextField();
        kelasTF    = new JTextField();
        btnSimpan  = new JButton("Simpan");

        add(new JLabel("NIM:"));      add(nimTF);
        add(new JLabel("Nama:"));     add(namaTF);
        add(new JLabel("Semester:")); add(semesterTF);
        add(new JLabel("Kelas:"));    add(kelasTF);
        add(new JLabel(""));          add(btnSimpan);

        btnSimpan.addActionListener(e -> simpanDataCaraI());
        setLocationRelativeTo(null);
    }

    // CARA I: menggunakan tanda '?' sebagai parameter
    private void simpanDataCaraI() {
        try {
            PreparedStatement pStat = koneksi.prepareStatement(
                "INSERT INTO datamhs (nim, nama, semester, kelas)" +
                " VALUE (?, ?, ?, ?)"
            );

            pStat.setString(1, nimTF.getText());
            pStat.setString(2, namaTF.getText());
            pStat.setString(3, semesterTF.getText());
            pStat.setString(4, kelasTF.getText());

            // Langkah 3: eksekusi - WAJIB dipanggil agar data tersimpan
            pStat.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            kosongkanField();

        } catch (SQLException e) {
            System.out.println("Gagal simpan: " + e.toString());
        }
    }

    // CARA II: menggunakan string concatenation langsung
    private void simpanDataCaraII() {
        try {
            PreparedStatement pStat = koneksi.prepareStatement(
                "INSERT INTO datamhs (nim, nama, semester, kelas)" +
                " VALUES ('" + nimTF.getText() + "','" +
                               namaTF.getText() + "','" +
                               semesterTF.getText() + "','" +
                               kelasTF.getText() + "')"
            );

            pStat.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            kosongkanField();

        } catch (SQLException e) {
            System.out.println("Gagal simpan: " + e.toString());
        }
    }

    private void kosongkanField() {
        nimTF.setText("");
        namaTF.setText("");
        semesterTF.setText("");
        kelasTF.setText("");
    }

    public static void main(String[] args) {
        new TambahDataForm().setVisible(true);
    }
}
