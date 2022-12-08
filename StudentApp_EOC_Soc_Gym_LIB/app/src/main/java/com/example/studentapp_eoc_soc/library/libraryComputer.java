package com.example.studentapp_eoc_soc.library;
public class libraryComputer {
    private int computerId;
    private String computerName;
    private int computerFloor;
    private String computerDesc;
    private String computerStatus;

    public libraryComputer( int computerId, String computerName, int computerFloor, String computerDesc, String computerStatus) {
        this.computerId = computerId;
        this.computerName = computerName;
        this.computerFloor = computerFloor;
        this.computerDesc = computerDesc;
        this.computerStatus = computerStatus;
    }

    public int getComputerId() {
        return computerId;
    }

    public void setComputerId(int computerId) {
        this.computerId = computerId;
    }

    public String getComputerName() {
        return computerName;
    }

    public int getComputerFloor() {
        return computerFloor;
    }

    public String getComputerDesc() {
        return computerDesc;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public void setComputerFloor(int computerFloor) {
        this.computerFloor = computerFloor;
    }

    public void setComputerDesc(String computerDesc) {
        this.computerDesc = computerDesc;
    }

    public String getComputerStatus() {
        return computerStatus;
    }

    public void setComputerStatus(String computerStatus) {
        this.computerStatus = computerStatus;
    }
}
