<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.UserDTO, model.GameMachineDTO, java.util.List" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    boolean isAdmin = "admin".equals(user.getRole());
    List<GameMachineDTO> machines = (List<GameMachineDTO>) request.getAttribute("MACHINES");
    String message = (String) request.getAttribute("MESSAGE");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách máy</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>💻 Danh sách máy</h2>

    <% if (message != null) { %>
        <p class="message success"><%= message %></p>
    <% } %>

    <table>
        <tr>
            <th>ID</th><th>Tên máy</th><th>Trạng thái</th><th>Hành động</th>
        </tr>
        <% for (GameMachineDTO m : machines) { %>
        <tr>
            <% if (isAdmin) { %>
            <form action="GameMachineServlet" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="machineId" value="<%= m.getMachineId() %>">
                <td><%= m.getMachineId() %></td>
                <td><input type="text" name="name" value="<%= m.getName() %>"></td>
                <td>
                    <select name="status">
                        <option value="available" <%= "available".equals(m.getStatus()) ? "selected" : "" %>>available</option>
                        <option value="in_use" <%= "in_use".equals(m.getStatus()) ? "selected" : "" %>>in_use</option>
                        <option value="maintenance" <%= "maintenance".equals(m.getStatus()) ? "selected" : "" %>>maintenance</option>
                    </select>
                </td>
                <td>
                    <button type="submit" class="btn">Cập nhật</button>
                    <a href="GameMachineServlet?action=delete&id=<%= m.getMachineId() %>" class="btn btn-danger">Xoá</a>
                </td>
            </form>
            <% } else { %>
            <td><%= m.getMachineId() %></td>
            <td><%= m.getName() %></td>
            <td><%= m.getStatus() %></td>
            <td>
                <% if ("available".equals(m.getStatus())) { %>
                    <form action="BookingServlet" method="post">
                        <input type="hidden" name="action" value="book">
<input type="hidden" name="machineId" value="${machine.machineId}" />                        <button type="submit" class="btn">Đặt máy</button>
                    </form>
                <% } else { %>
                    Không khả dụng
                <% } %>
            </td>
            <% } %>
        </tr>
        <% } %>
    </table>

    <a href="dashboard.jsp" class="back-link">🏠 Quay lại </a>
</div>
</body>
</html>
