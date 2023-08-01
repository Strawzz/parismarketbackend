package com.capstone.parismarketbackend.model;
import lombok.Data;
import org.springframework.beans.factory.support.ManagedArray;

@Data

public class Market {
    private String name;
    private String category;
    private Integer parisQuarter;
    private String address;
    private String hours;

//    public Market(String name, String category, Integer parisQuarter, String address, String hours){
//        this.name = name;
//        this.category = category;
//        this.parisQuarter = parisQuarter;
//        this.address = address;
//        this.hours = hours;
//    }
}
