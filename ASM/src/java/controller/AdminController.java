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

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO loginUser = (UserDTO) (session != null ? session.getAttribute("LOGIN_USER") : null);

        if (loginUser == null || !"admin".equals(loginUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            GameMachineDAO machineDAO = new GameMachineDAO();

            List<UserDTO> users = userDAO.getAllUsers();
            List<GameMachineDTO> machines = machineDAO.getAllMachines();

            request.setAttribute("USERS", users);
            request.setAttribute("MACHINES", machines);

            request.getRequestDispatcher("manage.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("ERROR", "Lỗi khi tải dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
}
