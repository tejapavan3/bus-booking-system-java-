package com.ford.assesment.dao;

import com.ford.assesment.model.BusDetails;
import com.ford.assesment.model.BusMaster;
import com.ford.assesment.model.PassengerInfo;
import com.ford.assesment.model.Reservation;
import com.ford.assesment.service.BusService;
import com.ford.datecircus.MyClass;
import com.ford.jdbc.connections.ConnectionClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationManager {
    List<BusMaster> busMasterList;
    List<PassengerInfo> currentPassengerList;
    List<Reservation> currentReservationList;

    ConnectionClass conClass;
    Connection myCon;
    ResultSet rs;
    PreparedStatement pstmt;
    static BufferedWriter bWriter;

    public ReservationManager() {
        conClass=new ConnectionClass() ;
        myCon=conClass.getMyConnection();
    }

    public List<BusMaster> getBusMasterList() {
        return busMasterList;
    }

    public void setBusMasterList(List<BusMaster> busMasterList) {
        this.busMasterList = busMasterList;
    }

    public List<PassengerInfo> getCurrentPassengerList() {
        return currentPassengerList;
    }

    public void setCurrentPassengerList(List<PassengerInfo> currentPassengerList) {
        this.currentPassengerList = currentPassengerList;
    }

    public List<Reservation> getCurrentReservationList() {
        return currentReservationList;
    }

    public void setCurrentReservationList(List<Reservation> currentReservationList) {
        this.currentReservationList = currentReservationList;
    }

    public void writeCSVDetailsToBusDetailsTable(){
        File file=new File("C:\\Users\\mpavante\\Desktop\\Assesment\\Bus-Details.csv");
        String line="";
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
            bufferedReader.readLine();

            while((line=bufferedReader.readLine())!=null){
                String[] s=line.split(",");
                BusDetails busDetails=new BusDetails(s[0],Integer.parseInt(s[1]),Integer.parseInt(s[2]),Integer.parseInt(s[3]),Integer.parseInt(s[4]),Integer.parseInt(s[5]),Integer.parseInt(s[6]),Integer.parseInt(s[7]));
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<BusDetails>> constraintViolations = validator.validate(busDetails);
                if (constraintViolations.size() > 0) {
                    System.out.println(constraintViolations.iterator().next().getMessage());
                    BusService.writeBusDetailsErrorToTextFile(constraintViolations.iterator().next().getMessage(), busDetails);
                }else{
                PreparedStatement pstmt= myCon.prepareStatement("insert into busdetails values (?,?,?,?,?,?,?,?)");
                pstmt.setString(1,s[0]);
                pstmt.setInt(2,Integer.parseInt(s[1]));
                pstmt.setInt(3,Integer.parseInt(s[2]));
                pstmt.setInt(4,Integer.parseInt(s[3]));
                pstmt.setInt(5,Integer.parseInt(s[4]));
                pstmt.setInt(6,Integer.parseInt(s[5]));
                pstmt.setInt(7,Integer.parseInt(s[6]));
                pstmt.setInt(8,Integer.parseInt(s[7]));

                pstmt.execute();}

            }} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public boolean writeCsvDetailsToBusMasterTable(){
        File file=new File("C:\\Users\\mpavante\\Desktop\\Assesment\\Bus-Master.csv");
        String line="";
        try {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
            bufferedReader.readLine();
            while((line=bufferedReader.readLine())!=null){
                String[] s=line.split(",");

                MyClass myClass = new MyClass();
                java.util.Date date = BusService.stringToDateConverter(s[3]);
                java.sql.Date date1 = BusService.utilToSqlDateConverter(date);
                String[] min = s[5].split(" ");
                BusMaster busMaster = new BusMaster(s[0],s[1],s[2],date1,s[4],Integer.parseInt(min[0]),Integer.parseInt(s[6]),s[7]);

                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<BusMaster>> constraintViolations = validator.validate(busMaster);

                if (constraintViolations.size() > 0) {
                    System.out.println(constraintViolations.iterator().next().getMessage());
                    BusService.writeBusMasterErrorToTextFile(constraintViolations.iterator().next().getMessage(), busMaster);
                }else{
                PreparedStatement pstmt= myCon.prepareStatement("insert into busmaster values(?,?,?,?,?,?,?,?)");
                pstmt.setString(1,s[0]);
                pstmt.setString(2,s[1]);
                pstmt.setString(3,s[2]);
                pstmt.setDate(4,date1);
                pstmt.setString(5,s[4]);
                pstmt.setInt(6,Integer.parseInt(min[0]));
                pstmt.setInt(7,Integer.parseInt(s[6]));
                pstmt.setString(8,s[7]);

                pstmt.execute();}


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


    public void loadBusMasterList(){
        busMasterList=new ArrayList<BusMaster>();

        try {
           // PreparedStatement pstmt= myCon.executeStatement("select * from busmaster");
           // rs= pstmt.executeQuery();
            Statement s=myCon.createStatement();
            rs=s.executeQuery("select * from busmaster");
            while(rs.next()){
                BusMaster busMaster=new BusMaster();
                busMaster.setBusNumber(rs.getString(1));
                busMaster.setStartPoint(rs.getString(2));
                busMaster.setEndPoint(rs.getString(3));
                busMaster.setStartingDate(rs.getDate(4));
                busMaster.setStartingTime(rs.getString(5));
                busMaster.setJourneyTime(rs.getInt(6));
                busMaster.setTotalStops(rs.getInt(7));
                busMaster.setType(rs.getString(8));

                busMasterList.add(busMaster);
            }
            List<BusDetails> res =loadBusDetailsList();
            for(BusMaster b:busMasterList){
                for(BusDetails k:res){
                    if(b.getBusNumber()==k.getBusNumber()){
                        b.setBusDetails(k);
                    }
                }

            }
         /*   for(BusMaster c:busMasterList){
                System.out.println(c);
            } */

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<BusDetails> loadBusDetailsList(){
        List<BusDetails> busDetailsList= new ArrayList<BusDetails>();
        try {
            pstmt=myCon.prepareStatement("select * from busDETAILS");
            rs= pstmt.executeQuery();

            while(rs.next()){
                BusDetails busDetails=new BusDetails();
                busDetails.setBusNumber(rs.getString(1));
                busDetails.setTotalSeats(rs.getInt(2));
                busDetails.setTotalCommonSeats(rs.getInt(3));
                busDetails.setTotalWomenOnlySeats(rs.getInt(4));
                busDetails.setTotalSpecialSeats(rs.getInt(5));
                busDetails.setAvailableCommonSeats(rs.getInt(6));
                busDetails.setAvailableWomenSeats(rs.getInt(7));
                busDetails.setAvailableSpecialSeats(rs.getInt(8));

                busDetailsList.add((busDetails));
            }

            /* for(BusDetails b:busDetailsList){
                 System.out.println(b);

             } */
        } catch (SQLException e) {
            e.printStackTrace();
        } return  busDetailsList;
    }

    public Reservation bookTicket(PassengerInfo passengerInfo){
        int count=0;

        if(currentPassengerList == null)
        {
            currentPassengerList = (ArrayList<PassengerInfo>) PassengerInfo.populatePassenger(passengerInfo);
        }
        else if(!currentPassengerList.contains(passengerInfo)){
            currentPassengerList.add(passengerInfo);
        }
      //  loadBusMasterList();
        for(BusMaster b:busMasterList){

        if(passengerInfo.getTravelDate().equals(b.getStartingDate())){

            if(passengerInfo.getStartingPoint().equals(b.getStartPoint()) &&
                    passengerInfo.getEndingPoint().equals(b.getEndPoint())){
                String bn=b.getBusNumber();
             if(passengerInfo.isSpecialSeatNeeded()){
                 if(b.getBusDetails().getAvailableSpecialSeats()>0){

                     try {
                         Statement pstmt1;
                         pstmt=myCon.prepareStatement("update busdetails set availablespecailseats=availablespecailseats-1 where busnumber=? ");
                         pstmt.setString(1,bn);
                         pstmt.execute();

                         count+=1;
                         System.out.println("special seat ticket booked...");
                         Reservation sa= new Reservation(count,passengerInfo,b);
                        // currentReservationList.add(sa);
                         if(currentReservationList==null){
                             currentReservationList=Reservation.populateReservation(sa);
                         }else{
                             currentReservationList.add(sa);
                         }
                         return sa;

                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                 }
             }if(passengerInfo.isWomanOnlySeatNeeded()){
                    if(b.getBusDetails().getAvailableWomenSeats()>0){
                        try {
                            pstmt=myCon.prepareStatement("update busdetails set availablewomenseats=availablewomenseats-1 where busnumber=?");
                            pstmt.setString(1,bn);
                            pstmt.execute();
                            count+=1;
                            System.out.println("women seat ticket booked ...");
                            Reservation sa= new Reservation(count,passengerInfo,b);
                           // currentReservationList.add(sa);
                            if(currentReservationList==null){
                                currentReservationList=Reservation.populateReservation(sa);
                            }else{
                                currentReservationList.add(sa);
                            }
                            return sa;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }

             }else{if(b.getBusDetails().getAvailableCommonSeats()>0){
                    try {
                        pstmt= myCon.prepareStatement("update busdetails set availablecommonseats=availablecommonseats-1 where busnumber=?");
                        pstmt.setString(1,bn);
                        pstmt.execute();
                        count+=1;
                        System.out.println("common seat ticket booked");
                        Reservation sa= new Reservation(count,passengerInfo,b);
                       // currentReservationList.add(sa);
                        if(currentReservationList==null){
                            currentReservationList=Reservation.populateReservation(sa);
                        }else{
                            currentReservationList.add(sa);
                        }
                        return sa;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }




                }

            }
        }


        } return null;


    }

    public boolean cancelTicket(Reservation reservation){
        boolean result=false;
        if(currentReservationList.contains(reservation)){
            PassengerInfo passengerInfo= reservation.getPassengerInfo();
            String busNo=reservation.getBusMaster().getBusNumber();
            if(passengerInfo.isSpecialSeatNeeded()){
                try {
                    pstmt= myCon.prepareStatement("update busdetails set availablespecailseats=availablespecailseats+1 where busnumber=? ");
                    pstmt.setString(1,busNo);
                    pstmt.execute();
                    System.out.println("reservation is cancelled...");
                    result=true;
                    currentReservationList.remove(reservation);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }if(passengerInfo.isWomanOnlySeatNeeded()){
                try {
                    pstmt = myCon.prepareStatement("update busdetails set availablewomanseats=availablewomanseats+1 where busnumber=? ");
                    pstmt.setString(1, busNo);
                    System.out.println("reservation is cancelled...");
                    currentReservationList.remove(reservation);
                    result=true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }else {
                try {
                    pstmt = myCon.prepareStatement("update busdetails set availablecommonseats=availablecommonseats+1 where busnumber=? ");
                    pstmt.setString(1, busNo);
                    System.out.println("reservation is cancelled...");
                    currentReservationList.remove(reservation);
                    result=true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }else{
            System.out.println("Reservation doesnt exist ");
        } return result;
    }

    public List<Reservation> getAllReservationsForBus(String busNo){
         List<Reservation> reservations=new ArrayList<Reservation>();
         for(Reservation r:currentReservationList){
             if((r.getBusMaster().getBusNumber()==busNo)){
                if(reservations==null){
                    reservations=Reservation.populateReservation(r);
                }else{
                    reservations.add(r);
                }
             }
         }return reservations;
    }

    public List<BusMaster> suggestAlternateTravelPlan(PassengerInfo passengerInfo){
        List<BusMaster> alternateBusMasterList = new ArrayList<BusMaster>();
        for (BusMaster b: busMasterList) {
            if ((passengerInfo.getStartingPoint().equals(b.getStartPoint())) && (passengerInfo.getEndingPoint().equals(b.getEndPoint()))){
                alternateBusMasterList.add(b);
            }}

       for(BusMaster a: busMasterList){
            for(BusMaster c: busMasterList) {
                if ((passengerInfo.getStartingPoint().equals(a.getStartPoint()))&&((a.getEndPoint()).equals(c.getStartPoint()))&&((passengerInfo.getEndingPoint()).equals(c.getEndPoint()))) {
                    alternateBusMasterList.add(a);
                    alternateBusMasterList.add(c);
                    break;
                }
            }
        }
        for(BusMaster d:alternateBusMasterList){

            System.out.println(d);
        }
       // System.out.println(alternateBusMasterList);
        return alternateBusMasterList;
    }


    public void writeAllReservationsToFile()  {
        String input = "[\"BusId\",\"From\",\"To\",\"Date\",\"Time\",\"Pass_Name\",\"Age\",\"Sex\",\"Seat_Number\",\"Seat_Preference\"]";
        input = input.substring(1, input.length() - 1);
        String[] split = input.split(" ");
        FileWriter writer = null;
        try {
            writer = new FileWriter("C:\\Users\\mpavante\\Desktop\\Assesment\\Reservation.csv");
            for (String s : split) {
                String[] split2 = s.split(",");

                writer.write(Arrays.asList(split2).stream().collect(Collectors.joining(",")));
            }
            writer.write("\n");

            for(Reservation res: currentReservationList)
            {
                String pattern = "dd-MMM-yyyy";
                DateFormat df = new SimpleDateFormat(pattern);

                String busId = res.getBusMaster().getBusNumber();
                String From = res.getBusMaster().getStartPoint();
                String to = res.getBusMaster().getEndPoint();
                String date = df.format(res.getBusMaster().getStartingDate());
                String Time = res.getBusMaster().getStartingTime();
                String Pass_Name = res.getPassengerInfo().getName();
                String age = String.valueOf(res.getPassengerInfo().getAge());
                char sex = res.getPassengerInfo().getSex();
                String seatNumber = String.valueOf(res.getSeatNumber());
                String seatPreference;
                if(res.getPassengerInfo().isSpecialSeatNeeded()){
                    seatPreference="Special Seat";
                }
                if(res.getPassengerInfo().isWomanOnlySeatNeeded())
                {seatPreference="Woman seat";
                }
                else
                { seatPreference ="Common Seat";}

                writer.write(busId);
                writer.write(",");
                writer.write(From);
                writer.write(",");
                writer.write(to);
                writer.write(",");
                writer.write(date);
                writer.write(",");
                writer.write(Time);
                writer.write(",");
                writer.write(Pass_Name);
                writer.write(",");
                writer.write(age);
                writer.write(",");
                writer.write(sex);
                writer.write(",");
                writer.write(seatNumber);
                writer.write(",");
                writer.write(seatPreference);
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }




       /* public static void main(String[] args) {
        ReservationManager reservationManager=new ReservationManager();
       // reservationManager.writeCSVDetailsToBusDetailsTable();
        //reservationManager.writeCsvDetailsToBusMasterTable();
          //  reservationManager.loadBusMasterList();
            String format="yyyy-MM-dd";
            SimpleDateFormat t=new SimpleDateFormat(format);
            Date d;
        /*    try {
                // d=t.parse("2022-8-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //t.format() */

       /*     PassengerInfo passengerInfo=new PassengerInfo(1,"pavan",'m',21,"Chennai","Bangalore",new Date(122,7,01),false,false);


            reservationManager.loadBusMasterList();
            Reservation r=reservationManager.bookTicket(passengerInfo);
            System.out.println(r); */

        /*    for(Reservation p: reservationManager.currentReservationList){
                System.out.println(p);
            } */

            // System.out.println(r);
         // reservationManager.cancelTicket(reservationManager.currentReservationList.get(0));
          /*  for(Reservation p: reservationManager.currentReservationList){
                System.out.println(p);
            } */

          //  reservationManager.writeAllReservationsToFile();
           // reservationManager.suggestAlternateTravelPlan(passengerInfo); */




    }





