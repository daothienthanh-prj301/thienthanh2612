<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.UserDTO, model.BookingDTO, java.util.List" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("BOOKINGS");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>👤 Thông tin cá nhân</h2>

    <form action="UserServlet" method="post">
        <input type="hidden" name="action" value="updateSelf">

        <label>Username:</label>
        <input type="text" value="<%= user.getUsername() %>" disabled>

        <label>Họ tên:</label>
        <input type="text" name="fullname" value="<%= user.getFullname() %>" required>

        <label>Mật khẩu:</label>
        <input type="password" name="password" value="<%= user.getPassword() %>" required>

        <label>Số dư:</label>
        <input type="text" value="<%= user.getBalance() %> VNĐ" disabled>

        <button type="submit" class="btn">Cập nhật</button>
    </form>

    <% if (request.getAttribute("MESSAGE") != null) { %>
        <p class="message success"><%= request.getAttribute("MESSAGE") %></p>
    <% } %>
    <% if (request.getAttribute("ERROR") != null) { %>
        <p class="message error"><%= request.getAttribute("ERROR") %></p>
    <% } %>

    <h2>🗂️ Lịch sử đặt máy</h2>
    <% if (bookings != null && !bookings.isEmpty()) { %>
        <table>
            <tr>
                <th>ID</th><th>Máy</th><th>Bắt đầu</th><th>Kết thúc</th><th>Tổng tiền</th>
            </tr>
            <% for (BookingDTO b : bookings) { %>
            <tr>
                <td><%= b.getBookingId() %></td>
                <td><%= b.getMachineId() %></td>
                <td><%= b.getStartTime() %></td>
                <td><%= b.getEndTime() %></td>
                <td><%= b.getTotalCost() %> VNĐ</td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>Chưa có lịch sử đặt máy.</p>
    <% } %>

    <a href="dashboard.jsp" class="back-link">🏠 Quay lại </a>
</div>
</body>
</html>
