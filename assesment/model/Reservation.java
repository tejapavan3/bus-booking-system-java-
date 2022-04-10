package com.ford.assesment.model;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    int seatNumber;
    PassengerInfo passengerInfo;
    BusMaster busMaster;

    public Reservation() {
    }

    public Reservation(int seatNumber, PassengerInfo passengerInfo, BusMaster busMaster) {
        this.seatNumber = seatNumber;
        this.passengerInfo = passengerInfo;
        this.busMaster = busMaster;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerInfo passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public BusMaster getBusMaster() {
        return busMaster;
    }

    public void setBusMaster(BusMaster busMaster) {
        this.busMaster = busMaster;
    }

    public static List<Reservation> populateReservation(Reservation r){
        List<Reservation> reservations=new ArrayList<>();
        reservations.add(r);
        return reservations;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "seatNumber=" + seatNumber +
                ", passengerInfo=" + passengerInfo +
                ", busMaster=" + busMaster +
                '}';
    }
}
