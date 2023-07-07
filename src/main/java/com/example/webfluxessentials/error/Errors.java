package com.example.webfluxessentials.error;

public enum Errors {

    SUCCESS(0, "Success"),
    USERNAME_ALREADY_TAKEN(1, "Username is already taken by other user");
    private final Integer code;
    private final String message;

    Errors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }



    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
