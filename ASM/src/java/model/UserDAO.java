package model;

import utils.DbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserDTO checkLogin(String username, String password) throws Exception {
        String sql = "SELECT * FROM [User] WHERE username=? AND password=?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("role"),
                    rs.getDouble("balance")
                );
            }
        }
        return null;
    }

    public List<UserDTO> getAllUsers() throws Exception {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM [User]";
        try (Connection conn = DbUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("role"),
                    rs.getDouble("balance")
                ));
            }
        }
        return list;
    }
    public UserDTO getUserById(int userId) throws Exception {
    String sql = "SELECT * FROM [User] WHERE user_id = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("role"),
                    rs.getDouble("balance")
                );
            }
        }
    }
    return null;
}
    public boolean insertUser(UserDTO user) throws Exception {
    String sql = "INSERT INTO [User](username, password, fullname, role, balance) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFullname());
        ps.setString(4, user.getRole());
        ps.setDouble(5, user.getBalance());
        return ps.executeUpdate() > 0;
    }
}
    public boolean deleteUser(int id) throws Exception {
        String sql = "DELETE FROM [User] WHERE user_id = ?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateUser(UserDTO user) throws Exception {
        String sql = "UPDATE [User] SET username=?, password=?, fullname=?, role=?, balance=? WHERE user_id=?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getRole());
            ps.setDouble(5, user.getBalance());
            ps.setInt(6, user.getUserId());
            return ps.executeUpdate() > 0;
        }
    }
    public boolean updateBalance(int userId, double newBalance) throws Exception {
    String sql = "UPDATE [User] SET balance = ? WHERE user_id = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDouble(1, newBalance);
        ps.setInt(2, userId);
        return ps.executeUpdate() > 0;
    }
}

}
