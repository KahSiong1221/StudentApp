package com.example.student_app.soc_portal;

public class SocEnrolment {
    private int userId;
    private int socId;
    private String userContact;

    public SocEnrolment(int userId, int socId, String userContact) {
        setUserId(userId);
        setSocId(socId);
        setUserContact(userContact);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSocId() {
        return socId;
    }

    public void setSocId(int socId) {
        this.socId = socId;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }
}
