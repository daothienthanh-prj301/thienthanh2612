<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>❌ Lỗi hệ thống</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #121212;
            color: #eee;
            text-align: center;
            padding-top: 50px;
        }
        h1 {
            color: #ff5555;
        }
        p {
            margin-top: 20px;
        }
        a {
            display: inline-block;
            margin-top: 30px;
            padding: 10px 20px;
            background: #555;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        a:hover {
            background: #777;
        }
    </style>
</head>
<body>
    <h1>🚫 Đã xảy ra lỗi hệ thống</h1>
    <p><strong>Thông tin lỗi:</strong></p>
    <p><%= request.getAttribute("ERROR") != null ? request.getAttribute("ERROR") : "Không rõ lỗi!" %></p>
    <a href="login.jsp">⬅️ Quay lại trang đăng nhập</a>
</body>
</html>
