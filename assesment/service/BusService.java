package com.ford.assesment.service;

import com.ford.assesment.model.BusDetails;
import com.ford.assesment.model.BusMaster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BusService {
  // public BusDAO busDAO ;
    private static BufferedWriter bWriter;
    static File f1=new File("C:\\Users\\mpavante\\Desktop\\Assesment\\error.txt");

    public BusService(){}

        public static java.util.Date stringToDateConverter(String stringDate) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
            dateFormat.setLenient(false);
            try {
                return dateFormat.parse(stringDate);
            } catch (ParseException pe) {
                return null;
            }
        }

        public static java.sql.Date utilToSqlDateConverter(java.util.Date utDate) {
            java.sql.Date sqlDate = null;
            if (utDate != null) {
                sqlDate = new java.sql.Date(utDate.getTime());
            }
            return sqlDate;
        }

    public static void writeBusDetailsErrorToTextFile(String message, BusDetails busDetails){
        try {
            bWriter = new BufferedWriter(new FileWriter(f1,true));
            bWriter.write(message+" -> "+busDetails + "\n");
            bWriter.flush();
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBusMasterErrorToTextFile(String message, BusMaster busMaster){
        try {
            bWriter = new BufferedWriter(new FileWriter(f1,true));
            bWriter.write(message+" -> "+busMaster + "\n");
            bWriter.flush();
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

