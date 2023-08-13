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

}
