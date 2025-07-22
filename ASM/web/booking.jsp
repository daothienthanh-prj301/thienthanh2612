<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.UserDTO, model.GameMachineDTO, model.BookingDTO, java.util.List" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<GameMachineDTO> machines = (List<GameMachineDTO>) request.getAttribute("MACHINES");
    List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("BOOKINGS");
    String message = (String) request.getAttribute("MESSAGE");
    String error = (String) request.getAttribute("ERROR");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt máy</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>🎮 Đặt máy chơi game</h2>

    <%
        boolean hasAvailableMachine = false;
        if (machines != null) {
            for (GameMachineDTO m : machines) {
                if ("Available".equalsIgnoreCase(m.getStatus())) {
                    hasAvailableMachine = true;
                    break;
                }
            }
        }
    %>

    <% if (hasAvailableMachine) { %>
        <form action="BookingServlet" method="post">
            <input type="hidden" name="action" value="book">
            <label>Chọn máy:</label>
            <select name="machineId" required>
                <% for (GameMachineDTO m : machines) {
                       if ("Available".equalsIgnoreCase(m.getStatus())) { %>
                    <option value="<%= m.getMachineId() %>"><%= m.getName() %> - <%= m.getStatus() %></option>
                <% }} %>
            </select>

            <label>Thời gian bắt đầu:</label>
            <input type="datetime-local" name="start" required>

            <label>Thời gian kết thúc:</label>
            <input type="datetime-local" name="end" required>

            <button type="submit" class="btn">Đặt máy</button>
        </form>
    <% } else { %>
        <p>⚠️ Hiện không có máy khả dụng để đặt.</p>
    <% } %>

    <hr>

    <h3>📋 Lịch sử đặt máy</h3>

    <% if (bookings != null && !bookings.isEmpty()) { %>
    <table>
        <thead>
            <tr>
                <th>Mã đặt</th>
                <th>Tên máy</th>
                <th>Bắt đầu</th>
                <th>Kết thúc</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
        <% for (BookingDTO b : bookings) { %>
            <tr>
                <td><%= b.getBookingId() %></td>
                <td><%= b.getMachineName() %></td>
                <td><%= b.getStartTime() %></td>
                <td><%= b.getEndTime() %></td>
                <td><%= b.getTotalCost() %></td>
                <td><%= b.getStatus() %></td>
                <td>
                    <%-- Huỷ đặt máy --%>
                    <form action="BookingServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="cancel">
                        <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
                        <input type="hidden" name="machineId" value="<%= b.getMachineId() %>">
                        <button type="submit" class="btn danger">Huỷ</button>
                    </form>

                    <%-- Nút "Tôi đã thanh toán" nếu trạng thái là PENDING --%>
                    <% if ("PENDING".equalsIgnoreCase(b.getStatus())) { %>
                    <form action="BookingServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="paid">
                        <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
                        <button type="submit" class="btn success">Tôi đã thanh toán</button>
                    </form>
                    <% } %>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
        <p>Chưa có lịch sử đặt máy.</p>
    <% } %>

    <% if (message != null) { %>
        <p class="message success"><%= message %></p>
    <% } %>
    <% if (error != null) { %>
        <p class="message error"><%= error %></p>
    <% } %>

    <a href="dashboard.jsp" class="back-link">🔙 Quay lại</a>
</div>
</body>
</html>
