package com.unpam.view;

public class PesanDialog {
    // Di aplikasi web, konfirmasi dilakukan langsung tanpa dialog
    // Class ini dibuat agar tidak perlu banyak mengubah kode Model
    public int showKonfirmasi(String pesan) {
        return 0; // selalu konfirmasi "Ya"
    }
}
