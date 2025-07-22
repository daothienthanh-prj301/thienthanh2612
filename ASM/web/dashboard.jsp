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
    <title>Dashboard</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>Xin chào, <%= user.getFullname() %>!</h2>
    <p>Số dư: <%= user.getBalance() %> VNĐ</p>

    <% if ("admin".equals(user.getRole())) { %>

<a href="MainController?action=manage" class="btn">Quản lý người dùng và máy</a>

    <% } else { %>
<a href="BookingServlet?action=list" class="btn">Đặt máy</a>
<a href="GameMachineServlet?action=list" class="btn">Danh sách máy</a>
        <a href="payment.jsp" class="btn">Nạp tiền</a>
        <a href="userinfo.jsp" class="btn">Thông tin cá nhân</a>
    <% } %>

    <form action="MainController" method="post" class="logout-form">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="btn btn-danger">Đăng xuất</button>
    </form>
</div>
</body>
</html>
