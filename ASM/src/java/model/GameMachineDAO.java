package model;

import utils.DbUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameMachineDAO {

    public List<GameMachineDTO> getAllMachines() throws Exception {
        List<GameMachineDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM GameMachine";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GameMachineDTO machine = new GameMachineDTO(
                    rs.getInt("machine_id"),
                    rs.getString("name"),
                    rs.getString("status")
                );
                System.out.println("MÁY: " + machine.getMachineId() + " - " + machine.getName() + " - " + machine.getStatus());
                list.add(machine);
            }
        }
        return list;
    }

    public boolean addMachine(GameMachineDTO machine) throws Exception {
        String sql = "INSERT INTO GameMachine(name, status) VALUES (?, ?)";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, machine.getName());
            ps.setString(2, machine.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateMachine(GameMachineDTO machine) throws Exception {
        String sql = "UPDATE GameMachine SET name = ?, status = ? WHERE machine_id = ?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, machine.getName());
            ps.setString(2, machine.getStatus());
            ps.setInt(3, machine.getMachineId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteMachine(int id) throws Exception {
        String sql = "DELETE FROM GameMachine WHERE machine_id = ?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int id, String status) throws Exception {
        String sql = "UPDATE GameMachine SET status = ? WHERE machine_id = ?";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<GameMachineDTO> getAvailableMachines() throws Exception {
        List<GameMachineDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM GameMachine WHERE status = 'available'";
        try (Connection conn = DbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GameMachineDTO machine = new GameMachineDTO(
                    rs.getInt("machine_id"),
                    rs.getString("name"),
                    rs.getString("status")
                );
                list.add(machine);
            }
        }
        return list;
    }
    public String getMachineNameById(int id) throws Exception {
    String sql = "SELECT name FROM GameMachine WHERE machine_id = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("name");
            }
        }
    }
    return null;
}
public GameMachineDTO getMachineById(int machineId) throws Exception {
    String sql = "SELECT machine_id, name, status FROM GameMachine WHERE machine_id = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, machineId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new GameMachineDTO(
                    rs.getInt("machine_id"),
                    rs.getString("name"),
                    rs.getString("status")
                );
            }
        }
    }
    return null;
}


}
