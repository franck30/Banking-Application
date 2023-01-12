package com.franck.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbstractEntity{



    private String name;
}
