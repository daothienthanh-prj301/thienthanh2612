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
    <title>Nạp tiền</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>💰 Nạp tiền vào tài khoản</h2>

    <p>Xin chào, <strong><%= user.getFullname() %></strong></p>
    <p>Số dư hiện tại: <strong><%= String.format("%,.0f", user.getBalance()) %> VNĐ</strong></p>

    <form action="PaymentServlet" method="post">
        <label>Nhập số tiền muốn nạp (tối thiểu 1.000 VNĐ):</label>
        <input type="number" name="amount" min="1000" step="1000" required>
        <button type="submit" class="btn">Nạp tiền</button>
    </form>

    <% if (request.getAttribute("MESSAGE") != null) { %>
        <p class="message success"><%= request.getAttribute("MESSAGE") %></p>
    <% } %>
    <% if (request.getAttribute("ERROR") != null) { %>
        <p class="message error"><%= request.getAttribute("ERROR") %></p>
    <% } %>

    <div class="qr-section">
        <h3>Hoặc quét mã QR để chuyển khoản</h3>
        <img src="images/mbbank.jpg" width="250" alt="QR Code MBBank">
        <p>Ghi nội dung: <strong><%= user.getUsername() %>_NAP</strong></p>
    </div>

    <a href="dashboard.jsp" class="back-link">🏠 Quay lại </a>
</div>
</body>
</html>
