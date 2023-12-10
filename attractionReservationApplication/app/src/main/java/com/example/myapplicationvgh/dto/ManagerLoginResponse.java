package com.example.myapplicationvgh.dto;

public class ManagerLoginResponse {
    // 아이디
    String id;

    // 로그인 성공 여부
    boolean loginSuccess;

    // 로그인 성공 메시지
    String message;

    String attraction;

    String name;

    public String getId() {
        return id;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public String getMessage() {
        return message;
    }

    public String getAttraction() {
        return attraction;
    }

    public String getName() {
        return name;
    }
}