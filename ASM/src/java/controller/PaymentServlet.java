package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.UserDAO;
import model.UserDTO;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String amountStr = request.getParameter("amount");

            if (amountStr == null || amountStr.isEmpty()) {
                request.setAttribute("ERROR", "Vui lòng nhập số tiền cần nạp.");
                request.getRequestDispatcher("payment.jsp").forward(request, response);
                return;
            }

            double amount = Double.parseDouble(amountStr);
            if (amount < 1000) {
                request.setAttribute("ERROR", "Số tiền nạp tối thiểu là 1.000 VNĐ.");
                request.getRequestDispatcher("payment.jsp").forward(request, response);
                return;
            }

            // Gửi dữ liệu mã QR (tĩnh)
            request.setAttribute("MESSAGE", "Vui lòng quét mã QR bên dưới để hoàn tất thanh toán.");
            request.setAttribute("AMOUNT", amount);
            request.setAttribute("QR_IMAGE", "images/qr-demo-momo.png"); // ảnh QR đặt trong /webapp/images/

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Số tiền không hợp lệ.");
        } catch (Exception e) {
            request.setAttribute("ERROR", "Lỗi hệ thống: " + e.getMessage());
        }

        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("dashboard.jsp"); // Không cho truy cập GET trực tiếp
    }
}
