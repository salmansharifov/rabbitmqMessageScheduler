package com.example.rabbitscheduler.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {
    private String name;
    private String address;
    private int age;
}
