package com.example.project;

public class HelperClass {
    String name, username, birthData;

    public HelperClass() {
    }
    public HelperClass(String name, String username, String birthData) {
        this.name = name;
        this.username = username;
        this.birthData = birthData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthData() {
        return birthData;
    }

    public void setBirthData(String birthData) {
        this.birthData = birthData;
    }
}
