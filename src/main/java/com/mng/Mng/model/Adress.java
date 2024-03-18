package com.mng.Mng.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "offices_adress")
@Embeddable
public class Adress {
    private String city;
    private String district;
    private String street;
    private String number;


}
