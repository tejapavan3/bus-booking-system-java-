package com.ford.assesment.model;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Timer;

public class BusMaster {
   @Pattern(regexp = "^[0-9]{3}+[a-zA-Z]{1}$",message = "Bus No is Invalid")
   private String BusNumber;
   private String StartPoint;
   private String EndPoint;
   private Date StartingDate;
   private String StartingTime;
   private int JourneyTime;
   private int TotalStops;
   private String Type;
   private BusDetails BusDetails;

   public BusMaster(String busNumber, String startPoint, String endPoint,
                    Date startingDate, String startingTime, int journeyTime,
                    int totalStops, String type) {
      BusNumber = busNumber;
      StartPoint = startPoint;
      EndPoint = endPoint;
      StartingDate = startingDate;
      StartingTime = startingTime;
      JourneyTime = journeyTime;
      TotalStops = totalStops;
      Type = type;
     // BusDetails=busDetails;
   }


   public BusMaster() {
   }

   public String getBusNumber() {
      return BusNumber;
   }

   public void setBusNumber(String busNumber) {
      BusNumber = busNumber;
   }

   public String getStartPoint() {
      return StartPoint;
   }

   public void setStartPoint(String startPoint) {
      StartPoint = startPoint;
   }

   public String getEndPoint() {
      return EndPoint;
   }

   public void setEndPoint(String endPoint) {
      EndPoint = endPoint;
   }

   public Date getStartingDate() {
      return StartingDate;
   }

   public void setStartingDate(Date startingDate) {
      StartingDate = startingDate;
   }

   public String getStartingTime() {
      return StartingTime;
   }

   public void setStartingTime(String startingTime) {
      StartingTime = startingTime;
   }

   public int getJourneyTime() {
      return JourneyTime;
   }

   public void setJourneyTime(int journeyTime) {
      JourneyTime = journeyTime;
   }

   public int getTotalStops() {
      return TotalStops;
   }

   public void setTotalStops(int totalStops) {
      TotalStops = totalStops;
   }

   public String getType() {
      return Type;
   }

   public void setType(String type) {
      Type = type;
   }

   public BusDetails getBusDetails() {
      return BusDetails;
   }

   public void setBusDetails(BusDetails busDetails) {
      this.BusDetails = busDetails;
   }

   @Override
   public String toString() {
      return "BusMaster{" +
              "BusNumber='" + BusNumber + '\'' +
              ", StartPoint='" + StartPoint + '\'' +
              ", EndPoint='" + EndPoint + '\'' +
              ", StartingDate=" + StartingDate +
              ", StartingTime='" + StartingTime + '\'' +
              ", JourneyTime=" + JourneyTime +
              ", TotalStops=" + TotalStops +
              ", Type='" + Type + '\'' +
              ", BusDetails=" + BusDetails +
              '}';
   }
}
