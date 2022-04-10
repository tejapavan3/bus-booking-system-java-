package com.ford.assesment.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class BusDetails {
    @Pattern(regexp = "^[0-9]{3}+[a-zA-Z]{1}$",message = "Bus No is Invalid")
    private String BusNumber;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int TotalSeats;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int TotalCommonSeats;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int TotalWomenOnlySeats;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int TotalSpecialSeats;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int AvailableCommonSeats;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int AvailableWomenSeats;

    @PositiveOrZero(message = "Number should be greater than zero")
    private int AvailableSpecialSeats;

    public BusDetails(String busNumber, int totalSeats, int totalCommonSeats, int totalWomenOnlySeats,
                      int totalSpecialSeats, int availableCommonSeats, int availableWomenSeats,
                      int availableSpecialSeats) {
        BusNumber = busNumber;
        TotalSeats = totalSeats;
        TotalCommonSeats = totalCommonSeats;
        TotalWomenOnlySeats = totalWomenOnlySeats;
        TotalSpecialSeats = totalSpecialSeats;
        AvailableCommonSeats = availableCommonSeats;
        AvailableWomenSeats = availableWomenSeats;
        AvailableSpecialSeats = availableSpecialSeats;
    }

    public BusDetails() {
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }

    public int getTotalSeats() {
        return TotalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        TotalSeats = totalSeats;
    }

    public int getTotalCommonSeats() {
        return TotalCommonSeats;
    }

    public void setTotalCommonSeats(int totalCommonSeats) {
        TotalCommonSeats = totalCommonSeats;
    }

    public int getTotalWomenOnlySeats() {
        return TotalWomenOnlySeats;
    }

    public void setTotalWomenOnlySeats(int totalWomenOnlySeats) {
        TotalWomenOnlySeats = totalWomenOnlySeats;
    }

    public int getTotalSpecialSeats() {
        return TotalSpecialSeats;
    }

    public void setTotalSpecialSeats(int totalSpecialSeats) {
        TotalSpecialSeats = totalSpecialSeats;
    }

    public int getAvailableCommonSeats() {
        return AvailableCommonSeats;
    }

    public void setAvailableCommonSeats(int availableCommonSeats) {
        AvailableCommonSeats = availableCommonSeats;
    }

    public int getAvailableWomenSeats() {
        return AvailableWomenSeats;
    }

    public void setAvailableWomenSeats(int availableWomenSeats) {
        AvailableWomenSeats = availableWomenSeats;
    }

    public int getAvailableSpecialSeats() {
        return AvailableSpecialSeats;
    }

    public void setAvailableSpecialSeats(int availableSpecialSeats) {
        AvailableSpecialSeats = availableSpecialSeats;
    }

    @Override
    public String toString() {
        return "BusDetails{" +
                "BusNumber='" + BusNumber + '\'' +
                ", TotalSeats=" + TotalSeats +
                ", TotalCommonSeats=" + TotalCommonSeats +
                ", TotalWomenOnlySeats=" + TotalWomenOnlySeats +
                ", TotalSpecialSeats=" + TotalSpecialSeats +
                ", AvailableCommonSeats=" + AvailableCommonSeats +
                ", AvailableWomenSeats=" + AvailableWomenSeats +
                ", AvailableSpecialSeats=" + AvailableSpecialSeats +
                '}';
    }
}
