package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import model.UserDAO;
import model.UserDTO;
import model.BookingDAO;
import model.BookingDTO;

@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

        if (loginUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        UserDAO userDAO = new UserDAO();
        BookingDAO bookingDAO = new BookingDAO();

        try {
            switch (action) {
                case "viewInfo":
                    List<BookingDTO> bookings = bookingDAO.getBookingsByUserIdWithMachineName(loginUser.getUserId());
                    request.setAttribute("BOOKINGS", bookings);
                    request.getRequestDispatcher("userinfo.jsp").forward(request, response);
                    break;

                case "updateSelf":
                    String fullname = request.getParameter("fullname");
                    String password = request.getParameter("password");

                    loginUser.setFullname(fullname);
                    loginUser.setPassword(password);

                    boolean updated = userDAO.updateUser(loginUser);
                    if (updated) {
                        session.setAttribute("LOGIN_USER", loginUser);
                        request.setAttribute("MESSAGE", "Cập nhật thành công!");
                    } else {
                        request.setAttribute("ERROR", "Cập nhật thất bại.");
                    }

                    List<BookingDTO> bookingList = bookingDAO.getBookingsByUserIdWithMachineName(loginUser.getUserId());
                    request.setAttribute("BOOKINGS", bookingList);
                    request.getRequestDispatcher("userinfo.jsp").forward(request, response);
                    break;

                case "viewAll":
                    if (!"admin".equals(loginUser.getRole())) {
                        response.sendRedirect("login.jsp");
                        return;
                    }

                    List<UserDTO> userList = userDAO.getAllUsers();
                    request.setAttribute("USERS", userList);
                    request.getRequestDispatcher("manage.jsp").forward(request, response);
                    break;

                case "delete":
                    if (!"admin".equals(loginUser.getRole())) {
                        response.sendRedirect("login.jsp");
                        return;
                    }

                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    userDAO.deleteUser(deleteId);
                    response.sendRedirect("UserServlet?action=viewAll");
                    break;

                case "update":
                    if (!"admin".equals(loginUser.getRole())) {
                        response.sendRedirect("login.jsp");
                        return;
                    }

                    int userId = Integer.parseInt(request.getParameter("userId"));
                    String username = request.getParameter("username");
                    String newPassword = request.getParameter("password");
                    String newFullname = request.getParameter("fullname");
                    String role = request.getParameter("role");
                    double balance = Double.parseDouble(request.getParameter("balance"));

                    UserDTO userToUpdate = new UserDTO(userId, username, newPassword, newFullname, role, balance);
                    userDAO.updateUser(userToUpdate);
                    response.sendRedirect("UserServlet?action=viewAll");
                    break;

                default:
                    response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            request.setAttribute("ERROR", "Lỗi xử lý UserServlet: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
}
