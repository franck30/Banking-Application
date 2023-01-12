package com.franck.example.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
public class Addresse extends AbstractEntity{



    private String street;

    private Integer houseNumber;

    private Integer zipCode;

    private String city;

    @OneToOne
    private User user;

    private String country;
}
