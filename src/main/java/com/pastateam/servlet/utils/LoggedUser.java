package com.pastateam.servlet.utils;

public class LoggedUser {
    public enum UserType {
        AZIENDA,
        PERSONA
    }

    private final Integer ID;
    private final UserType type;

    public LoggedUser(Integer ID, UserType type) {
        this.ID = ID;
        this.type = type;
    }

    public Integer getID() {
        return ID;
    }

    public UserType getType() {
        return type;
    }
}
