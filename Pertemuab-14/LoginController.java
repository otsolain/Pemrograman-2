package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Koneksi;
import com.unpam.view.MainForm;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String konten   = "";

        MainForm mainForm = new MainForm();

        if (username == null || password == null) {
            // Tampilkan form login
            konten = "<br><h3>Login</h3>"
                   + "<form method='POST' action='LoginController'>"
                   + "Username: <input type='text' name='username'><br><br>"
                   + "Password: <input type='password' name='password'><br><br>"
                   + "<input type='submit' value='Login'>"
                   + "</form>";
        } else {
            // Proses login
            try {
                Enkripsi enkripsi  = new Enkripsi();
                String passHash    = enkripsi.hashMD5(password);
                Koneksi koneksi    = new Koneksi();
                Connection conn    = koneksi.getConnection();

                if (conn != null) {
                    PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM tbuser WHERE username=? AND password=?"
                    );
                    ps.setString(1, username);
                    ps.setString(2, passHash);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("userName", rs.getString("nama"));
                        session.setAttribute("username", username);
                        konten = "<br><h1>Selamat Datang</h1><h2>" + rs.getString("nama") + "</h2>";
                    } else {
                        konten = "<br><h3 style='color:red;'>Username atau Password salah!</h3>"
                               + "<a href='LoginController'>Kembali</a>";
                    }
                    conn.close();
                }
            } catch (Exception ex) {
                konten = "<br><p>Error: " + ex.getMessage() + "</p>";
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
