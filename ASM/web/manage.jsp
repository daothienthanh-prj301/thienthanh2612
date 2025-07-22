<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.UserDTO, model.GameMachineDTO, java.util.List" %>
<%
    UserDTO admin = (UserDTO) session.getAttribute("LOGIN_USER");
    if (admin == null || !"admin".equals(admin.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<UserDTO> users = (List<UserDTO>) request.getAttribute("USERS");
    List<GameMachineDTO> machines = (List<GameMachineDTO>) request.getAttribute("MACHINES");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>👑 Quản lý người dùng</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Họ tên</th>
            <th>Password</th>
            <th>Role</th>
            <th>Balance</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <% 
            if (users != null && !users.isEmpty()) {
                for (UserDTO u : users) { 
        %>
        <tr>
            <td><%= u.getUserId() %></td>
            <td>
                <form action="UserServlet" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="userId" value="<%= u.getUserId() %>">
                    <input type="text" name="username" value="<%= u.getUsername() %>">
            </td>
            <td>
                    <input type="text" name="fullname" value="<%= u.getFullname() %>">
            </td>
            <td>
                    <input type="text" name="password" value="<%= u.getPassword() %>">
            </td>
            <td>
                    <select name="role">
                        <option value="user" <%= "user".equals(u.getRole()) ? "selected" : "" %>>user</option>
                        <option value="admin" <%= "admin".equals(u.getRole()) ? "selected" : "" %>>admin</option>
                    </select>
            </td>
            <td>
                    <input type="number" name="balance" value="<%= u.getBalance() %>">
            </td>
            <td>
                    <button type="submit" class="btn">Cập nhật</button>
                    <a href="UserServlet?action=delete&id=<%= u.getUserId() %>"
                       onclick="return confirm('Bạn có chắc muốn xoá người dùng này?')"
                       class="btn btn-danger">Xoá</a>
                </form>
            </td>
        </tr>
        <% 
                }
            } else { 
        %>
        <tr><td colspan="7">Không có dữ liệu người dùng.</td></tr>
        <% } %>
        </tbody>
    </table>

    <!-- PHẦN QUẢN LÝ MÁY GIỮ NGUYÊN -->
    <h2>💻 Quản lý máy</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Tên máy</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        <% 
            if (machines != null) {
                for (GameMachineDTO m : machines) { 
        %>
        <tr>
            <td><%= m.getMachineId() %></td>
            <td>
                <form action="GameMachineServlet" method="post">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="machineId" value="<%= m.getMachineId() %>">
                    <input type="text" name="name" value="<%= m.getName() %>">
            </td>
            <td>
                    <select name="status">
                        <option value="available" <%= "available".equals(m.getStatus()) ? "selected" : "" %>>available</option>
                        <option value="in_use" <%= "in_use".equals(m.getStatus()) ? "selected" : "" %>>in_use</option>
                        <option value="maintenance" <%= "maintenance".equals(m.getStatus()) ? "selected" : "" %>>maintenance</option>
                    </select>
            </td>
            <td>
                    <button type="submit" class="btn">Cập nhật</button>
                    <a href="GameMachineServlet?action=delete&id=<%= m.getMachineId() %>"
                       onclick="return confirm('Bạn có chắc muốn xoá máy này?')"
                       class="btn btn-danger">Xoá</a>
                </form>
            </td>
        </tr>
        <% 
                }
            } else { 
        %>
        <tr><td colspan="4">Không có dữ liệu máy chơi game.</td></tr>
        <% } %>
    </table>

    <a href="dashboard.jsp" class="btn">🏠 Quay lại </a>
</div>
</body>
</html>
