package model;

public class UserDTO {
    private int userId;
    private String username;
    private String password;
    private String fullname;
    private String role;
    private double balance;

    
    public UserDTO(int userId, String username, String password, String fullname, String role, double balance) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

   
}
