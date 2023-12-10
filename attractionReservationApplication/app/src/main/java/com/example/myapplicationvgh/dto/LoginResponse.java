package com.example.myapplicationvgh.dto;
public class LoginResponse {
    // 아이디
    String id;

    // 로그인 성공 여부
    boolean loginSuccess;

    // 로그인 성공 메시지
    String message;

    //어느 어트랙션을 타고 있는지
    private String reservAttraction;

    public String getId() {
        return id;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public String getMessage() {
        return message;
    }

    public String getReservAttraction()
    {
        return message;
    }
}