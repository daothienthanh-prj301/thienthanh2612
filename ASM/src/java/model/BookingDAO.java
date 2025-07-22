package model;

import utils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<BookingDTO> getAllBookingsWithMachineName() throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        String sql = "SELECT b.*, g.name FROM Booking b JOIN GameMachine g ON b.machine_id = g.machine_id";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookingDTO b = new BookingDTO(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("machine_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getDouble("total_cost")
                );
                b.setMachineName(rs.getString("name"));
                list.add(b);
            }
        }
        return list;
    }

    public List<BookingDTO> getBookingsByUserIdWithMachineName(int userId) throws Exception {
        List<BookingDTO> list = new ArrayList<>();
        String sql = "SELECT b.*, g.name FROM Booking b JOIN GameMachine g ON b.machine_id = g.machine_id WHERE b.user_id = ?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookingDTO b = new BookingDTO(
                            rs.getInt("booking_id"),
                            rs.getInt("user_id"),
                            rs.getInt("machine_id"),
                            rs.getTimestamp("start_time"),
                            rs.getTimestamp("end_time"),
                            rs.getDouble("total_cost")
                    );
                    b.setMachineName(rs.getString("name"));
                    list.add(b);
                }
            }
        }
        return list;
    }

    public boolean deleteBooking(int bookingId) throws Exception {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        }
    }
    


public boolean insertBooking(int userId, int machineId, Timestamp startTime, Timestamp endTime, double totalCost) throws Exception {
    String sql = "INSERT INTO Booking (user_id, machine_id, start_time, end_time, total_cost) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ps.setInt(2, machineId);
        ps.setTimestamp(3, startTime);
        ps.setTimestamp(4, endTime);
        ps.setDouble(5, totalCost);

        return ps.executeUpdate() > 0;
    }
}
public boolean updatePaymentStatus(int bookingId, String status) throws Exception {
    String sql = "UPDATE Booking SET status = ? WHERE booking_id = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setInt(2, bookingId);
        return ps.executeUpdate() > 0;
    }
}

}
