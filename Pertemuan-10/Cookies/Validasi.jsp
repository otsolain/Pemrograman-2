<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.Cookie"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Validasi Login</title>
</head>
<body>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if(username != null && password != null &&
       username.equals("ADMIN") && password.equals("ADMIN")) {

        session.setAttribute("userLogin", username);
        session.setMaxInactiveInterval(60*60*24);

        Cookie userCookie = new Cookie("user", username);
        userCookie.setMaxAge(60*60*24);
        response.addCookie(userCookie);

        out.println("<h2>Login berhasil</h2>");
        out.println("<p>User : " + username + "</p>");
        out.println("<p>Session dan cookie berhasil dibuat.</p>");
    } else {
        out.println("<h2>Login gagal</h2>");
        out.println("<p>User atau password salah.</p>");
    }
%>

<br>
<a href="index.jsp">Kembali</a>
</body>
</html>
