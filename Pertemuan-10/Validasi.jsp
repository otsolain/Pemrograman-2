<%-- File: Validasi.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Validasi login (username & password = ADMIN)
    if (username.equals("ADMIN") && password.equals("ADMIN")) {

        // === SESSION ===
        // Simpan data ke dalam session
        session.setAttribute("userLogin", username);
        session.setAttribute("statusLogin", "Sudah Login");

        // Tentukan masa berlaku session: 1 hari
        session.setMaxInactiveInterval(60 * 60 * 24);

        // === COOKIE ===
        // Simpan waktu login terakhir ke cookie
        Cookie lastVisitCookie = new Cookie("lastVisit", new Date().toString());
        lastVisitCookie.setMaxAge(60 * 60 * 24); // umur cookie 1 hari
        response.addCookie(lastVisitCookie);

        // Cookie menyimpan username
        Cookie userCookie = new Cookie("user", username);
        userCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(userCookie);

        // Redirect ke halaman utama
        response.sendRedirect("beranda.jsp");

    } else {
        // Login gagal: simpan pesan error ke session lalu redirect kembali
        session.setAttribute("pesanError", "Username atau Password salah!");
        response.sendRedirect("index.jsp");
    }
%>
