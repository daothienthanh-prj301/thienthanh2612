package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import model.BookingDAO;
import model.BookingDTO;
import model.UserDTO;
import model.GameMachineDAO;
import model.GameMachineDTO;

@WebServlet(name = "BookingServlet", urlPatterns = {"/BookingServlet"})
public class BookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserDTO loginUser = (session != null) ? (UserDTO) session.getAttribute("LOGIN_USER") : null;

        if (loginUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        BookingDAO bookingDAO = new BookingDAO();
        GameMachineDAO machineDAO = new GameMachineDAO();

        try {
            if ("book".equals(action)) {
                String machineIdRaw = request.getParameter("machineId");
                String start = request.getParameter("start");
                String end = request.getParameter("end");

                if (machineIdRaw == null || start == null || end == null ||
                        machineIdRaw.isEmpty() || start.isEmpty() || end.isEmpty()) {
                    request.setAttribute("ERROR", "❌ Dữ liệu đặt máy không hợp lệ.");
                } else {
                    try {
                        int machineId = Integer.parseInt(machineIdRaw);
                        Timestamp startTime = Timestamp.valueOf(start.replace("T", " ") + ":00");
                        Timestamp endTime = Timestamp.valueOf(end.replace("T", " ") + ":00");

                        boolean inserted = bookingDAO.insertBooking(loginUser.getUserId(), machineId, startTime, endTime, 0);
                        if (inserted) {
                            machineDAO.updateStatus(machineId, "in_use");
                            String machineName = machineDAO.getMachineNameById(machineId);
                            request.setAttribute("MESSAGE", "✅ Đặt máy thành công: " + machineName);
                        } else {
                            request.setAttribute("ERROR", "❌ Đặt máy thất bại.");
                        }
                    } catch (IllegalArgumentException | NullPointerException e) {
                        request.setAttribute("ERROR", "❌ Định dạng thời gian không hợp lệ.");
                    } catch (Exception e) {
                        request.setAttribute("ERROR", "❌ Lỗi đặt máy: " + e.getMessage());
                    }
                }
            } else if ("cancel".equals(action)) {
                try {
                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                    int machineId = Integer.parseInt(request.getParameter("machineId"));

                    bookingDAO.deleteBooking(bookingId);
                    machineDAO.updateStatus(machineId, "available");

                    request.setAttribute("MESSAGE", "✅ Đã huỷ lượt đặt thành công.");
                } catch (Exception e) {
                    request.setAttribute("ERROR", "❌ Huỷ đặt máy thất bại: " + e.getMessage());
                }
            } else if ("paid".equals(action)) {
                try {
                    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
                    boolean updated = bookingDAO.updatePaymentStatus(bookingId, "PAID");
                    if (updated) {
                        request.setAttribute("MESSAGE", "✅ Đã xác nhận thanh toán.");
                    } else {
                        request.setAttribute("ERROR", "❌ Cập nhật thất bại.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("ERROR", "❌ Có lỗi xảy ra khi xử lý thanh toán.");
                }
            }

            // Nếu là user thì lấy máy khả dụng
            if (!"admin".equals(loginUser.getRole())) {
                List<GameMachineDTO> availableMachines = machineDAO.getAvailableMachines();
                request.setAttribute("MACHINES", availableMachines);
            }

            // Lấy lịch sử booking
            List<BookingDTO> bookings = "admin".equals(loginUser.getRole())
                    ? bookingDAO.getAllBookingsWithMachineName()
                    : bookingDAO.getBookingsByUserIdWithMachineName(loginUser.getUserId());

            request.setAttribute("BOOKINGS", bookings);

            // Trả về trang booking.jsp
            request.getRequestDispatcher("booking.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "❌ Hệ thống gặp lỗi: " + e.getMessage());
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
