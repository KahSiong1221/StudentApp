package com.example.studentapp_eoc_soc.library;

public class libraryRoom {
    private int RoomId;
    private String RoomName;
    private int roomFloor;
    private String RoomDesc;
    private String RoomStatus;

    public libraryRoom(int roomId, String roomName, int roomFloor, String roomDesc, String roomStatus) {
        this.RoomId = roomId;
        this.RoomName = roomName;
        this.roomFloor = roomFloor;
        this.RoomDesc = roomDesc;
        this.RoomStatus = roomStatus;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public String getRoomDesc() {
        return RoomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        RoomDesc = roomDesc;
    }

    public String getRoomStatus() {
        return RoomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        RoomStatus = roomStatus;
    }
}
