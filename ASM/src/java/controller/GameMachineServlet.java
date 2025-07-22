package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import model.GameMachineDAO;
import model.GameMachineDTO;
import model.UserDTO;

@WebServlet(name = "GameMachineServlet", urlPatterns = {"/GameMachineServlet"})
public class GameMachineServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

        
        if (loginUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            GameMachineDAO dao = new GameMachineDAO();

            switch (action) {
                case "list":
                    List<GameMachineDTO> machines = dao.getAllMachines();
                    request.setAttribute("MACHINES", machines);

                    if ("admin".equals(loginUser.getRole())) {
                        request.getRequestDispatcher("manage.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("machine.jsp").forward(request, response);
                    }
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    dao.deleteMachine(deleteId);
                    response.sendRedirect("AdminController");
                    break;

                case "update":
                    int updateId = Integer.parseInt(request.getParameter("machineId"));
                    String name = request.getParameter("name");
                    String status = request.getParameter("status");

                    GameMachineDTO machine = new GameMachineDTO(updateId, name, status);
                    dao.updateMachine(machine);
                    response.sendRedirect("AdminController");
                    break;

                default:
                    response.sendRedirect("error.jsp");
            }

        } catch (Exception e) {
            request.setAttribute("ERROR", e.getMessage());
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
