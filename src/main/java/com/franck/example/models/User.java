package com.franck.example.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_user")
@SuperBuilder
@Entity
public class User extends AbstractEntity{



    private String firstname;

    private String lastname;


    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne
    private Addresse addresse;

    private boolean active;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @OneToOne
    private Role role;

    @OneToOne
    private Account account;







}
