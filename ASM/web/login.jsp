<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body>
<div class="container">
    <h2>🔑 Đăng nhập Cyber Game Net</h2>

    <form action="MainController" method="post">
        <input type="hidden" name="action" value="login">

        <label>Tên đăng nhập:</label>
        <input type="text" name="username" required>

        <label>Mật khẩu:</label>
        <input type="password" name="password" required>

        <button type="submit" class="btn">Đăng nhập</button>
    </form>

    <% if (request.getAttribute("ERROR") != null) { %>
        <p class="message error"><%= request.getAttribute("ERROR") %></p>
    <% } %>
</div>
</body>
</html>
