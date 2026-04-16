package com.unpam.view;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

    public void tampilkan(HttpServletRequest request,
                          HttpServletResponse response,
                          String konten)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);

        // ─── Menu Sidebar Default ─────────────────────────────────
        String menu = "<br><b>Master Data</b><br>"
                + "<a href='MahasiswaController'>Mahasiswa</a><br>"
                + "<a href='MataKuliahController'>Mata Kuliah</a><br><br>"
                + "<b>Transaksi</b><br>"
                + "<a href='NilaiController'>Nilai</a><br><br>"
                + "<b>Laporan</b><br>"
                + "<a href='LaporanNilaiController'>Nilai</a><br><br>"
                + "<a href='LoginController'>Login</a><br>"
                + "<a href='LogoutController'>Logout</a><br><br>";

        // ─── Menu Navbar Atas Default ─────────────────────────────
        String topMenu = "<nav><ul>"
                + "<li><a href='MainForm'>Home</a></li>"

                + "<li><a href='#'>Master Data</a>"
                + "<ul>"
                + "<li><a href='MahasiswaController'>Mahasiswa</a></li>"
                + "<li><a href='MataKuliahController'>Mata Kuliah</a></li>"
                + "</ul></li>"

                + "<li><a href='#'>Transaksi</a>"
                + "<ul>"
                + "<li><a href='NilaiController'>Nilai</a></li>"
                + "</ul></li>"

                + "<li><a href='#'>Laporan</a>"
                + "<ul>"
                + "<li><a href='LaporanNilaiController'>Nilai</a></li>"
                + "</ul></li>"

                + "<li><a href='LoginController'>Login</a></li>"
                + "<li><a href='LogoutController'>Logout</a></li>"
                + "</ul></nav>";

        // ─── Cek Session Login ────────────────────────────────────
        String userName = "";
        if (!session.isNew()) {
            try {
                userName = session.getAttribute("userName").toString();
            } catch (Exception ex) { }

            if (userName != null && !userName.equals("")) {
                // Tampilkan konten sambutan jika konten kosong
                if (konten.equals("")) {
                    konten = "<br><h1>Selamat Datang</h1><h2>" + userName + "</h2>";
                }
                // Ambil menu dari session jika sudah di-set saat login
                try { menu    = session.getAttribute("menu").toString(); }    catch (Exception ex) { }
                try { topMenu = session.getAttribute("topMenu").toString(); } catch (Exception ex) { }
            }
        }

        // ─── Tulis HTML Output ────────────────────────────────────
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("  <meta charset='UTF-8'>");
            out.println("  <link href='style.css' rel='stylesheet' type='text/css'/>");
            out.println("  <title>Informasi Nilai Mahasiswa</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"#808080\">");
            out.println("<center>");
            out.println("<table width=\"80%\" bgcolor=\"#eeeeee\">");

            // Header
            out.println("  <tr>");
            out.println("    <td colspan=\"2\" align=\"center\">");
            out.println("      <br>");
            out.println("      <h2 style=\"margin-bottom:0px; margin-top:0px;\">Informasi Nilai Mahasiswa</h2>");
            out.println("      <h1 style=\"margin-bottom:0px; margin-top:0px;\">UNIVERSITAS PAMULANG</h1>");
            out.println("      <h4 style=\"margin-bottom:0px; margin-top:0px;\">");
            out.println("        Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten");
            out.println("      </h4>");
            out.println("      <br>");
            out.println("    </td>");
            out.println("  </tr>");

            // Konten utama: sidebar + area konten
            out.println("  <tr height=\"400\">");

            // Kolom kiri: Sidebar menu
            out.println("    <td width=\"200\" align=\"center\" valign=\"top\" bgcolor=\"#eeffee\">");
            out.println("      <div id='menu'>" + menu + "</div>");
            out.println("    </td>");

            // Kolom kanan: Navbar atas + konten
            out.println("    <td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\">");
            out.println("      " + topMenu);
            out.println("      <br>");
            out.println("      " + konten);
            out.println("    </td>");

            out.println("  </tr>");

            // Footer
            out.println("  <tr>");
            out.println("    <td colspan=\"2\" align=\"center\" bgcolor=\"#eeeeff\">");
            out.println("      <small>");
            out.println("        Copyright &copy; 2026 Universitas Pamulang<br>");
            out.println("        Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten<br>");
            out.println("      </small>");
            out.println("    </td>");
            out.println("  </tr>");

            out.println("</table>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { tampilkan(req, res, ""); }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException { tampilkan(req, res, ""); }
}
