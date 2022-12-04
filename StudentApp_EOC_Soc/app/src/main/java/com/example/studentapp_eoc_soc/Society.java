package com.example.studentapp_eoc_soc;

public class Society {
    private int socId;
    private String socName;

    public Society() {}

    public Society(String socName) {
        setSocName(socName);
    }

    public int getSocId() {
        return socId;
    }

    public void setSocId(int socId) {
        this.socId = socId;
    }

    public String getSocName() {
        return socName;
    }

    public void setSocName(String socName) {
        this.socName = socName;
    }
}
