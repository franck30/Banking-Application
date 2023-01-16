package com.franck.example.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
    @JoinColumn(name = "id_user")
    private User user;

    private String country;
}
