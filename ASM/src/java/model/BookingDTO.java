package model;

import java.sql.Timestamp;

public class BookingDTO {
    private int bookingId;
    private int userId;
    private int machineId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double totalCost;

    private String machineName; // ⚠️ THÊM DÒNG NÀY

    // Constructor
    public BookingDTO(int bookingId, int userId, int machineId, Timestamp startTime, Timestamp endTime, double totalCost) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.machineId = machineId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
    }

    // Getter/Setter cho machineName
    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    // Các getter/setter khác...
    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMachineId() {
        return machineId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
