package com.sandbox.ecommerce.entity;

import javax.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="country")
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private String code;
    
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="country")
    @JsonIgnore
    private List<State> states;
}