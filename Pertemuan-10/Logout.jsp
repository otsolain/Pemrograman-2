<%-- File: Logout.jsp --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Hapus semua session
    session.invalidate();

    // Hapus cookie dengan set MaxAge = 0
    Cookie userCookie      = new Cookie("user", "");
    Cookie lastVisitCookie = new Cookie("lastVisit", "");
    userCookie.setMaxAge(0);
    lastVisitCookie.setMaxAge(0);
    response.addCookie(userCookie);
    response.addCookie(lastVisitCookie);

    // Redirect ke halaman login
    response.sendRedirect("index.jsp");
%>
