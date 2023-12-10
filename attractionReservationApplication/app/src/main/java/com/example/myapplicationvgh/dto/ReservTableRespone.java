package com.example.myapplicationvgh.dto;

public class ReservTableRespone {
    int waitingNumber;
    int waitingTime;
    boolean reservCheck;

    public int getWaitingNumber() {
        return waitingNumber;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public boolean isReservCheck() {
        return reservCheck;
    }
}