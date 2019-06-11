package com.example.openapi.model.enums;

public enum Sex {
    male(1, "男性"), female(2, "女性");

    int code;
    String msg;

    Sex(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Sex{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
