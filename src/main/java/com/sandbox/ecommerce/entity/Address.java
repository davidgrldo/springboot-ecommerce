package com.sandbox.ecommerce.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name="address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "street")
    private String street;
    
    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;
    
    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}