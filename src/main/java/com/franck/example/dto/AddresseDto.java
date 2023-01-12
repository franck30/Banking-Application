package com.franck.example.dto;


import com.franck.example.models.Addresse;
import com.franck.example.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddresseDto {

    private Integer id;

    private String street;

    private Integer houseNumber;

    private Integer zipCode;

    private String city;

    private String country;

    private Integer userId;

    public static AddresseDto fromEntity(Addresse addresse) {
        return AddresseDto.builder()
                .id(addresse.getId())
                .street(addresse.getStreet())
                .houseNumber(addresse.getHouseNumber())
                .zipCode(addresse.getZipCode())
                .city(addresse.getCity())
                .country(addresse.getCountry())
                .userId(addresse.getUser().getId())
                .build();

    }


    public static Addresse toEntity(AddresseDto addresse) {
        return Addresse.builder()
                .id(addresse.getId())
                .street(addresse.getStreet())
                .houseNumber(addresse.getHouseNumber())
                .zipCode(addresse.getZipCode())
                .city(addresse.getCity())
                .country(addresse.getCountry())
                .user(User.builder().id(addresse.getUserId()).build())
                .build();

    }

}
