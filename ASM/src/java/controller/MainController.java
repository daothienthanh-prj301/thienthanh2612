package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import model.UserDAO;
import model.UserDTO;
import model.GameMachineDAO;
import model.GameMachineDTO;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = "login.jsp"; // Mặc định trả về trang login

        try {
            if ("login".equals(action)) {
                // Xử lý đăng nhập
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLogin(username, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", user);
                    url = "dashboard.jsp"; // Đăng nhập thành công chuyển đến dashboard
                } else {
                    request.setAttribute("ERROR", "Sai tài khoản hoặc mật khẩu!");
                }

            } else if ("logout".equals(action)) {
                // Xử lý đăng xuất
                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();
                url = "login.jsp";

            } else if ("manage".equals(action)) {
                // Trang quản lý người dùng và máy (chỉ admin được vào)
                HttpSession session = request.getSession(false);
                UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

                if (loginUser == null || !"admin".equals(loginUser.getRole())) {
                    url = "login.jsp"; // Nếu không phải admin, chuyển về login
                } else {
                    // Lấy dữ liệu danh sách user và máy
                    UserDAO userDAO = new UserDAO();
                    GameMachineDAO machineDAO = new GameMachineDAO();

                    List<UserDTO> users = userDAO.getAllUsers();
                    List<GameMachineDTO> machines = machineDAO.getAllMachines();

                    request.setAttribute("USERS", users);
                    request.setAttribute("MACHINES", machines);

                    url = "manage.jsp"; // Forward đến trang quản lý
                }

            } else if ("booking".equals(action)) {
                // Trang đặt máy - chỉ dành cho user (hoặc admin dùng cũng được)
                HttpSession session = request.getSession(false);
                UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

                if (loginUser == null) {
                    url = "login.jsp";
                } else {
                    GameMachineDAO machineDAO = new GameMachineDAO();
                    List<GameMachineDTO> availableMachines = machineDAO.getAvailableMachines();
                    request.setAttribute("MACHINES", availableMachines);
                    url = "booking.jsp";
                }

            } else {
                // Nếu không có action hoặc action không hợp lệ
                HttpSession session = request.getSession(false);
                UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

                if (loginUser != null) {
                    url = "dashboard.jsp";
                } else {
                    url = "login.jsp";
                }
            }

        } catch (Exception e) {
            log("ERROR at MainController: " + e.getMessage(), e);
            request.setAttribute("ERROR", "Lỗi hệ thống: " + e.getMessage());
            url = "error.jsp";
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
