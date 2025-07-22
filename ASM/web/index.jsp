<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang người dùng</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
    <div class="container">
        <h2>🎮 Xin chào, <%= user.getFullname() %>!</h2>
        <p>Số dư tài khoản: <strong><%= user.getBalance() %> VNĐ</strong></p>

        <div class="btn-group">
            <a class="btn" href="booking.jsp">Đặt máy</a>
            <a class="btn" href="payment.jsp">Nạp tiền</a>
            <% if ("admin".equals(user.getRole())) { %>
                <a class="btn" href="admin/dashboard.jsp">Trang quản trị</a>
            <% } %>
        </div>

        <form action="MainController" method="post" class="logout-form">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="btn btn-logout">Đăng xuất</button>
        </form>
    </div>
</body>
</html>
