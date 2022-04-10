package com.ford.assesment.client;

import com.ford.assesment.busdbmgmt.BusDBManager;
import com.ford.assesment.dao.ReservationManager;
import com.ford.assesment.model.PassengerInfo;
import com.ford.assesment.model.Reservation;

import java.util.Date;

public class BusClient {
    public static void main(String[] args) {
       // ReservationManager reservationManager=new ReservationManager();
        //PassengerInfo passengerInfo=new PassengerInfo(1,"pavan",'m',21,"Chennai","Bangalore",new Date(122,7,01),true,false);
        BusDBManager bd=new BusDBManager();
        bd.displayMenu();

    }
}
