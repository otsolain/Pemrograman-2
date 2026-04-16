package com.unpam.controller;

import com.unpam.model.Koneksi;
import com.unpam.view.MainForm;
import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import net.sf.jasperreports.engine.*;

@WebServlet(name = "LaporanNilaiController", urlPatterns = {"/LaporanNilaiController"})
public class LaporanNilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String aksi   = request.getParameter("aksi");
        String konten = "";
        MainForm mainForm = new MainForm();

        if ("cetak".equals(aksi)) {
            // Cetak laporan PDF menggunakan JasperReport
            OutputStream outputStream = null;
            try {
                // Path file .jrxml di folder Web Pages/reports/
                String jrxmlPath = getServletContext().getRealPath("/reports/NilaiReport.jrxml");

                // Kompilasi .jrxml menjadi .jasper
                JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

                // Parameter laporan
                Map<String, Object> params = new HashMap<>();
                params.put("REPORT_TITLE", "Laporan Nilai Mahasiswa");

                // Koneksi ke database
                Koneksi koneksiObj = new Koneksi();
                Connection conn    = koneksiObj.getConnection();

                // Isi data ke template
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

                // Export ke PDF
                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

                // Kirim PDF ke browser
                response.setContentType("application/pdf");
                response.setContentLength(pdfBytes.length);
                response.setHeader("Content-Disposition", "inline; filename=LaporanNilai.pdf");

                outputStream = response.getOutputStream();
                outputStream.write(pdfBytes);
                outputStream.flush();
                conn.close();
                return; // stop — jangan lanjut ke mainForm.tampilkan()

            } catch (Exception ex) {
                konten = "<p style='color:red;'><b>Gagal mencetak laporan!</b><br>"
                       + ex.getMessage() + "</p>"
                       + "<p><i>Pastikan file NilaiReport.jrxml ada di folder Web Pages/reports/</i></p>"
                       + "<a href='LaporanNilaiController'>Kembali</a>";
            } finally {
                if (outputStream != null) {
                    try { outputStream.close(); } catch (IOException e) {}
                }
            }

        } else {
            // Tampilkan halaman pilihan laporan
            konten = "<h3>Laporan Nilai Mahasiswa</h3>"
                   + "<table border='1' cellpadding='10' style='border-collapse:collapse;'>"
                   + "<tr style='background:#eef;'>"
                   + "<th>Nama Laporan</th><th>Format</th><th>Aksi</th>"
                   + "</tr>"
                   + "<tr>"
                   + "<td>Laporan Nilai Semua Mahasiswa</td>"
                   + "<td>PDF</td>"
                   + "<td><a href='LaporanNilaiController?aksi=cetak' target='_blank'>"
                   + "🖨 Cetak PDF</a></td>"
                   + "</tr>"
                   + "</table>";
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
