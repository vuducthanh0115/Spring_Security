package com.example.spring_security.basic.payload;

public class RandomStuff {
    private String message;

    public RandomStuff() {
    }

    public RandomStuff(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
