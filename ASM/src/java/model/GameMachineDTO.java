package model;

public class GameMachineDTO {
    private int machineId;
    private String name;
    private String status;

    public GameMachineDTO(int machineId, String name, String status) {
        this.machineId = machineId;
        this.name = name;
        this.status = status;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
