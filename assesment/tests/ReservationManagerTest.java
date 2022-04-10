package com.ford.assesment.dao;

import com.ford.assesment.model.BusMaster;
import com.ford.assesment.model.PassengerInfo;
import com.ford.assesment.model.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {
    ReservationManager reservationManager;

    @BeforeEach
    void setUp() {
         reservationManager=new ReservationManager();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldBookACommonticket(){
        PassengerInfo passengerInfo=new PassengerInfo(1,"pavan",'m',21,"Chennai","Bangalore",new Date(122,7,01),false,false);
       // reservationManager.setBusMasterList(new ArrayList<BusMaster>());
        reservationManager.loadBusMasterList();
        System.out.println(reservationManager.getBusMasterList());
        Reservation reservation =reservationManager.bookTicket(passengerInfo);
        boolean res=!(reservation.getPassengerInfo().isWomanOnlySeatNeeded()||reservation.getPassengerInfo().isSpecialSeatNeeded());
       // System.out.println(reservation);
        assertEquals(true,res);
    }

    @Test
    public void shouldBookWomenONlyTicket(){
        PassengerInfo passengerInfo=new PassengerInfo(1,"pavani",'m',21,"Chennai","Bangalore",new Date(122,7,01),false,true);
        // reservationManager.setBusMasterList(new ArrayList<BusMaster>());
        reservationManager.loadBusMasterList();
       // System.out.println(reservationManager.getBusMasterList());
        Reservation reservation =reservationManager.bookTicket(passengerInfo);
        boolean res=reservation.getPassengerInfo().isWomanOnlySeatNeeded();
       // System.out.println(reservation);
        assertEquals(true,res);

    }

    @Test
    public void shouldBookSpecialTicket(){
        PassengerInfo passengerInfo=new PassengerInfo(1,"pavani",'m',21,"Chennai","Bangalore",new Date(122,7,01),true,false);
        // reservationManager.setBusMasterList(new ArrayList<BusMaster>());
        reservationManager.loadBusMasterList();
        // System.out.println(reservationManager.getBusMasterList());
        Reservation reservation =reservationManager.bookTicket(passengerInfo);
        boolean res=reservation.getPassengerInfo().isSpecialSeatNeeded()&&(reservation.getPassengerInfo().getName()=="pavani");
       // System.out.println(reservation);
        System.out.println(reservationManager.currentReservationList);
        assertEquals(true,res);

    }

    @Test
    public void busNotAvailableDueToLackOfBusInThatRoute(){
        PassengerInfo passengerInfo=new PassengerInfo(1,"pavani",'m',21,"texas","pasadena",new Date(122,7,01),true,false);
        reservationManager.loadBusMasterList();
        Reservation reservation=reservationManager.bookTicket(passengerInfo);
        boolean isTicketBooked ;
        if(reservation==null){
            isTicketBooked=true;
            System.out.println("ticket not available in those routes...");

        }else isTicketBooked=false;

        assertTrue(isTicketBooked);


    }


    @Test
    public void busNotAvailableDueToLackOfSeats(){
        PassengerInfo passengerInfo=new PassengerInfo(1,"pavani",'m',21,"texas","pasadena",new Date(122,7,01),true,false);
        reservationManager.loadBusMasterList();
        Reservation reservation=reservationManager.bookTicket(passengerInfo);
        boolean isTicketBooked ;
        if(reservation==null){
            isTicketBooked=true;
            System.out.println("seats are not available in those routes....");

        }else isTicketBooked=false;

        assertTrue(isTicketBooked);


    }

   @Test
    public void shouldCancelTicket(){
       PassengerInfo passengerInfo=new PassengerInfo(10,"prabhas",'m',21,"Chennai","Bangalore",new Date(122,7,01),true,false);
       // reservationManager.setBusMasterList(new ArrayList<BusMaster>());
       reservationManager.loadBusMasterList();
       // System.out.println(reservationManager.getBusMasterList());
       Reservation reservation =reservationManager.bookTicket(passengerInfo);
       boolean isTicketCancelled=reservationManager.cancelTicket(reservation);
       assertTrue(isTicketCancelled);
    }

    @Test
    public void shouldReturnReservationListBasedOnBusNumber(){

        PassengerInfo passengerInfo=new PassengerInfo(1,"pavani",'m',21,"Chennai","Bangalore",new Date(122,7,01),true,false);
        reservationManager.loadBusMasterList();
        Reservation reservation =reservationManager.bookTicket(passengerInfo);

        List<Reservation> reservations =reservationManager.getAllReservationsForBus("122S");

        boolean res;
        if(reservations==null){
            res=false;
        }else res=true;

        for(Reservation r:reservations){
            System.out.println(r);
        }

        assertEquals(true,res);


    }



}