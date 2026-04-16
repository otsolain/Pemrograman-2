package com.unpam.model;

import java.sql.*;
import com.unpam.view.PesanDialog;

public class MataKuliah {

    private String kodeMataKuliah, namaMataKuliah;
    private int jumlahSks;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi         = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    // Getter & Setter
    public String getKodeMataKuliah()                       { return kodeMataKuliah; }
    public void setKodeMataKuliah(String kodeMataKuliah)    { this.kodeMataKuliah = kodeMataKuliah; }
    public String getNamaMataKuliah()                       { return namaMataKuliah; }
    public void setNamaMataKuliah(String namaMataKuliah)    { this.namaMataKuliah = namaMataKuliah; }
    public int getJumlahSks()                               { return jumlahSks; }
    public void setJumlahSks(int jumlahSks)                 { this.jumlahSks = jumlahSks; }
    public String getPesan()                                { return pesan; }
    public Object[][] getList()                             { return list; }
    public void setList(Object[][] list)                    { this.list = list; }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            boolean simpan   = false;
            String SQLStatemen = "";
            Statement sta = null;

            try {
                simpan       = true;
                SQLStatemen  = "INSERT INTO tbmatakuliah VALUES ('" +
                               kodeMataKuliah + "','" + namaMataKuliah + "','" + jumlahSks + "')";
                sta          = connection.createStatement();
                jumlahSimpan = sta.executeUpdate(SQLStatemen);

                if (simpan && jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data mata kuliah";
                }
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmatakuliah\n" + ex;
            } finally {
                try {
                    if (sta != null) sta.close();
                    connection.close();
                } catch (SQLException e) {}
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
}
