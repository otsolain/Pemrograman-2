package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.model.MataKuliah;
import com.unpam.model.Nilai;
import com.unpam.view.MainForm;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String aksi   = request.getParameter("aksi");
        String konten = "";
        MainForm mainForm = new MainForm();
        Nilai nilai       = new Nilai();

        if (aksi == null) {
            // Tampilkan daftar nilai
            if (nilai.tampilSemua()) {
                Object[][] list = nilai.getList();
                StringBuilder sb = new StringBuilder();
                sb.append("<h3>Data Nilai Mahasiswa</h3>");
                sb.append("<a href='NilaiController?aksi=tambah'>+ Input Nilai</a>&nbsp;&nbsp;");
                sb.append("<a href='NilaiController?aksi=cetak'>🖨 Cetak Laporan</a><br><br>");
                sb.append("<table border='1' cellpadding='6' style='border-collapse:collapse; font-size:90%;'>");
                sb.append("<tr style='background:#eef;'>"
                        + "<th>NIM</th><th>Nama</th><th>Mata Kuliah</th>"
                        + "<th>Tugas</th><th>UTS</th><th>UAS</th>"
                        + "<th>Nilai Akhir</th><th>Grade</th></tr>");
                if (list != null) {
                    for (Object[] row : list) {
                        sb.append("<tr>");
                        for (Object col : row) {
                            sb.append("<td>").append(col).append("</td>");
                        }
                        sb.append("</tr>");
                    }
                }
                sb.append("</table>");
                konten = sb.toString();
            } else {
                konten = "<p style='color:red;'>" + nilai.getPesan() + "</p>";
            }

        } else if (aksi.equals("tambah")) {
            // Tampilkan form input nilai
            // Ambil daftar mahasiswa
            Mahasiswa mhs = new Mahasiswa();
            mhs.tampilSemua();
            Object[][] listMhs = mhs.getList();

            // Ambil daftar matakuliah
            MataKuliah mk = new MataKuliah();
            mk.tampilSemua();
            Object[][] listMK = mk.getList();

            StringBuilder sb = new StringBuilder();
            sb.append("<h3>Input Nilai Mahasiswa</h3>");
            sb.append("<form method='POST' action='NilaiController'>");
            sb.append("<input type='hidden' name='aksi' value='simpan'>");

            // Dropdown mahasiswa
            sb.append("Mahasiswa: <select name='nim'>");
            if (listMhs != null) {
                for (Object[] row : listMhs) {
                    sb.append("<option value='").append(row[0]).append("'>")
                      .append(row[0]).append(" - ").append(row[1]).append("</option>");
                }
            }
            sb.append("</select><br><br>");

            // Dropdown mata kuliah
            sb.append("Mata Kuliah: <select name='kodeMataKuliah'>");
            if (listMK != null) {
                for (Object[] row : listMK) {
                    sb.append("<option value='").append(row[0]).append("'>")
                      .append(row[0]).append(" - ").append(row[1]).append("</option>");
                }
            }
            sb.append("</select><br><br>");

            sb.append("Nilai Tugas (30%): <input type='number' step='0.01' name='nilaiTugas' min='0' max='100'><br><br>");
            sb.append("Nilai UTS (30%): <input type='number' step='0.01' name='nilaiUTS' min='0' max='100'><br><br>");
            sb.append("Nilai UAS (40%): <input type='number' step='0.01' name='nilaiUAS' min='0' max='100'><br><br>");
            sb.append("<input type='submit' value='Simpan'>&nbsp;");
            sb.append("<a href='NilaiController'>Batal</a>");
            sb.append("</form>");
            konten = sb.toString();

        } else if (aksi.equals("simpan")) {
            // Proses simpan nilai
            try {
                nilai.setNim(request.getParameter("nim"));
                nilai.setKodeMataKuliah(request.getParameter("kodeMataKuliah"));
                nilai.setNilaiTugas(Double.parseDouble(request.getParameter("nilaiTugas")));
                nilai.setNilaiUTS(Double.parseDouble(request.getParameter("nilaiUTS")));
                nilai.setNilaiUAS(Double.parseDouble(request.getParameter("nilaiUAS")));

                if (nilai.simpan()) {
                    double na    = nilai.hitungNilaiAkhir();
                    String grade = nilai.hitungGrade();
                    konten = "<p style='color:green;'><b>Nilai berhasil disimpan!</b></p>"
                           + "<table border='1' cellpadding='6' style='border-collapse:collapse;'>"
                           + "<tr><td>Nilai Akhir</td><td><b>" + String.format("%.2f", na) + "</b></td></tr>"
                           + "<tr><td>Grade</td><td><b>" + grade + "</b></td></tr>"
                           + "</table><br>"
                           + "<a href='NilaiController'>Kembali ke Daftar</a>";
                } else {
                    konten = "<p style='color:red;'>Gagal: " + nilai.getPesan() + "</p>"
                           + "<a href='NilaiController?aksi=tambah'>Kembali</a>";
                }
            } catch (Exception ex) {
                konten = "<p>Error: " + ex.getMessage() + "</p>";
            }

        } else if (aksi.equals("cetak")) {
            // Cetak laporan menggunakan JasperReport
            // Pastikan file .jasper sudah ada di folder WEB-INF/report/
            try {
                String reportPath = getServletContext().getRealPath("/WEB-INF/report/LaporanNilai.jasper");
                com.unpam.model.Koneksi koneksiObj = new com.unpam.model.Koneksi();
                Connection conn = koneksiObj.getConnection();

                java.util.Map<String, Object> params = new java.util.HashMap<>();
                params.put("REPORT_TITLE", "Laporan Nilai Mahasiswa");

                byte[] bytes = net.sf.jasperreports.engine.JasperRunManager.runReportToPdf(
                    reportPath, params, conn
                );

                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.setHeader("Content-Disposition", "inline; filename=LaporanNilai.pdf");

                OutputStream os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
                return; // jangan lanjut ke mainForm.tampilkan()

            } catch (Exception ex) {
                konten = "<p style='color:red;'>Gagal mencetak laporan: " + ex.getMessage() + "</p>"
                       + "<p><i>Pastikan file JasperReport (.jasper) sudah ada di WEB-INF/report/</i></p>"
                       + "<a href='NilaiController'>Kembali</a>";
            }
        }

        mainForm.tampilkan(request, response, konten);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { processRequest(req, res); }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { processRequest(req, res); }
}
