package com.lab5.bai2;

public class JUnitMessage {
    private String message;

    public JUnitMessage(String message) {
        this.message = message;
    }

    public String printMessage() {
        System.out.println(message);
        return message;
    }

    public String getMessage() {
        return message;
    }

    public String printHiMessage() {
        String hiMessage = "Hi!" + message;
        System.out.println(hiMessage);
        return hiMessage;
    }
}
