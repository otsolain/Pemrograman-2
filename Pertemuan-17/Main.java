package com.rentcar.view;

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

        String menu = "<br><b>Master Data</b><br>"
                + "<a href='MobilController'>Data Mobil</a><br>"
                + "<a href='CustomerController'>Data Customer</a><br><br>"
                + "<b>Transaksi</b><br>"
                + "<a href='SewaController'>Penyewaan</a><br>"
                + "<a href='SewaController?aksi=kembali'>Pengembalian</a><br><br>"
                + "<b>Laporan</b><br>"
                + "<a href='LaporanSewaController'>Laporan Transaksi</a><br><br>"
                + "<a href='LoginController'>Login</a><br>"
                + "<a href='LogoutController'>Logout</a><br>";

        String topMenu = "<nav><ul>"
                + "<li><a href='MainForm'>Home</a></li>"
                + "<li><a href='#'>Master Data</a><ul>"
                + "<li><a href='MobilController'>Mobil</a></li>"
                + "<li><a href='CustomerController'>Customer</a></li>"
                + "</ul></li>"
                + "<li><a href='#'>Transaksi</a><ul>"
                + "<li><a href='SewaController'>Penyewaan</a></li>"
                + "<li><a href='SewaController?aksi=kembali'>Pengembalian</a></li>"
                + "</ul></li>"
                + "<li><a href='#'>Laporan</a><ul>"
                + "<li><a href='LaporanSewaController'>Laporan Transaksi</a></li>"
                + "</ul></li>"
                + "<li><a href='LoginController'>Login</a></li>"
                + "<li><a href='LogoutController'>Logout</a></li>"
                + "</ul></nav>";

        String userName = "";
        if (!session.isNew()) {
            try { userName = session.getAttribute("userName").toString(); } catch (Exception ex) {}
            if (userName != null && !userName.equals("")) {
                if (konten.equals("")) konten = "<br><h1>Selamat Datang</h1><h2>" + userName + "</h2>";
                try { menu    = session.getAttribute("menu").toString(); }    catch (Exception ex) {}
                try { topMenu = session.getAttribute("topMenu").toString(); } catch (Exception ex) {}
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<link href='style.css' rel='stylesheet' type='text/css'/>");
            out.println("<title>Aplikasi Rent Car</title></head>");
            out.println("<body bgcolor=\"#808080\"><center>");
            out.println("<table width=\"80%\" bgcolor=\"#eeeeee\">");
            out.println("<tr><td colspan=\"2\" align=\"center\">");
            out.println("<br><h2 style=\"margin:0;\">Aplikasi Penyewaan Mobil (Rent Car)</h2>");
            out.println("<h4 style=\"margin:0;\">Sistem Informasi Manajemen Kendaraan</h4><br>");
            out.println("</td></tr><tr height=\"450\">");
            out.println("<td width=\"200\" valign=\"top\" bgcolor=\"#eeffee\" align=\"center\">");
            out.println("<div id='menu'>" + menu + "</div></td>");
            out.println("<td valign=\"top\" bgcolor=\"#ffffff\" align=\"center\">");
            out.println(topMenu + "<br>" + konten + "</td></tr>");
            out.println("<tr><td colspan=\"2\" align=\"center\" bgcolor=\"#eeeeff\">");
            out.println("<small>Copyright &copy; 2026 Rent Car System</small></td></tr>");
            out.println("</table></center></body></html>");
        }
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { tampilkan(req, res, ""); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException { tampilkan(req, res, ""); }
}
