package com.ford.assesment.busdbmgmt;

import com.ford.assesment.dao.ReservationManager;
import com.ford.assesment.model.BusMaster;
import com.ford.assesment.model.PassengerInfo;
import com.ford.assesment.model.Reservation;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class BusDBManager {
    Scanner sc=new Scanner(System.in);
    String reply="yes";
    ReservationManager reservationManager;

    public void displayMenu(){
        reservationManager=new ReservationManager();
        reservationManager.loadBusMasterList();

        while(reply.equalsIgnoreCase("yes"))
        {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Welcome to Bus Booking System");
            System.out.println("Select 1 for Booking the Ticket");
            System.out.println("Select 2 for Cancelling the booked tickets");
            System.out.println("Select 3 for getting reservations done to a particular Bus");
            System.out.println("Select 4 to know alternate all travel options from Your Source To Destination");
            System.out.println("select 5 to write all reservations to a csv file...");
            //System.out.println(" from Your Source To Destination");
            System.out.println("Please enter your option");

           // s.nextLine();
            int selected = sc.nextInt();

            switch (selected)
            {
                case 1: {
                    System.out.println("Enter Passenger Details");
                    System.out.println("Enter passenger id");
                    long passengerId = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter passenger name");
                    String name = sc.nextLine();
                    System.out.println("Enter Sex (Male/Female)");
                    char sex = sc.nextLine().charAt(0);
                    System.out.println("Enter age of the passenger");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter starting point ");
                    String startingPoint = sc.nextLine();
                    System.out.println("Enter ending point ");
                    String endingPoint = sc.nextLine();
                    System.out.println("Enter travel date in the format (yyyy-MM-dd)");
                    Date travelDate = Date.valueOf(sc.nextLine());
                    System.out.println("Do you want to book a special seat? (true/false)");
                    boolean isSpecialSeatNeeded = sc.nextBoolean();
                    System.out.println("Do you want to book a women-only seat? (true/false)");
                    boolean isWomenOnlySeatNeeded = sc.nextBoolean();
                    sc.nextLine();
                    PassengerInfo passengerInfo = new PassengerInfo(passengerId,name,sex,age,startingPoint,endingPoint,travelDate,isSpecialSeatNeeded,isWomenOnlySeatNeeded);
                    Reservation reservation=reservationManager.bookTicket(passengerInfo);
                    if(reservation != null){
                        System.out.println("Your ticket is booked successfully");
                        System.out.println(" Your reservation ticket details are "+ reservation);
                    }
                    else{
                        System.out.println("Tickets Unavailable");
                    }
                    break;
                }

                case 2:
                    BusMaster busMasterObject = null;
                    PassengerInfo passengerInfoObject = null;
                    Reservation reservation = null;
                    System.out.println("Please provide your reservation details for cancellation");
                    System.out.println("Enter your passenger id");
                    long passengerId = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter your travel date in the format (yyyy-MM-dd)");
                    Date travelDate = Date.valueOf(sc.nextLine());
                    System.out.println("Enter your bus number");
                    String busNo = sc.nextLine();
                    System.out.println("Enter your seat number");
                    int seatNo = sc.nextInt();
                    sc.nextLine();
                    for(BusMaster busMaster:reservationManager.getBusMasterList()){
                        if(busMaster.getBusNumber().equalsIgnoreCase(busNo)){
                            busMasterObject = busMaster;
                            break;
                        }
                    }
                    if(reservationManager.getCurrentPassengerList()!=null) {
                        for (PassengerInfo passengerInfo : reservationManager.getCurrentPassengerList()){
                            if(passengerInfo.getPassengerId() == passengerId && passengerInfo.getTravelDate().equals(travelDate)){
                                passengerInfoObject = passengerInfo;
                                break;
                            }
                        }
                    }
                    System.out.println("Processing your request...");
                    if(busMasterObject == null && passengerInfoObject ==null) {
                        System.out.println("Reservation doesn't Exist");
                    }
                    else{
                        reservation = new Reservation(seatNo,passengerInfoObject,busMasterObject);
                        reservationManager.cancelTicket(reservation);
                    }


                    break;

                case 3:
                    System.out.println("Enter bus no to get the reservation list...");
                    String busNo1 = sc.nextLine();
                    List<Reservation> reservationObjects = reservationManager.getAllReservationsForBus(busNo1);
                    if(reservationObjects == null){
                        System.out.println("There are no reservations for this bus");
                    }
                    else {
                        for (Reservation reservation1 : reservationObjects)
                            System.out.println(reservation1);
                    }
                    break;


                case 4:
                    System.out.println("Enter Passenger Details");
                    System.out.println("Enter passenger id");
                    long passengerId1 = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Enter passenger name");
                    String name = sc.nextLine();
                    System.out.println("Sex (Male/Female)");
                    char sex = sc.nextLine().charAt(0);
                    System.out.println("Enter age of the passenger");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter starting point of the journey");
                    String startingPoint = sc.nextLine();
                    System.out.println("Enter ending point of the journey");
                    String endingPoint = sc.nextLine();
                    System.out.println("Enter travel date in the format (yyyy-MM-dd)");
                    Date travelDate1 = Date.valueOf(sc.nextLine());
                    System.out.println("Do you want  to book a special seat? (true/false)");
                    boolean isSpecialSeatNeeded = sc.nextBoolean();
                    System.out.println("Do you want to book a women-only seat? (true/false)");
                    boolean isWomenOnlySeatNeeded = sc.nextBoolean();
                    sc.nextLine();
                    PassengerInfo passengerInfo = new PassengerInfo(passengerId1,name,sex,age,startingPoint,endingPoint,travelDate1,isSpecialSeatNeeded,isWomenOnlySeatNeeded);
                    List<BusMaster> busMasterList = reservationManager.suggestAlternateTravelPlan(passengerInfo);
                    for(BusMaster busMaster:busMasterList) {
                        System.out.println(busMaster);
                    }

                    break;

                case 5:
                    reservationManager.writeAllReservationsToFile();
                    break;


                default:
                    System.out.println("Not a valid option...");
                    break;
            }
            System.out.println("Press Yes if you want to continue, No to exit");
            reply=sc.next();
        }
        System.out.println("exiting the menu..");


    }
}
