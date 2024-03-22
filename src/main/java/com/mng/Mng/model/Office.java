package com.mng.Mng.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "offices")

public class Office extends BaseEntity{

    private String officeName;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "city", column = @Column(name = "city")),
            @AttributeOverride( name = "district", column = @Column(name = "district")),
            @AttributeOverride( name = "street", column = @Column(name = "street")),
            @AttributeOverride( name = "number", column = @Column(name = "number")),
    })

    private Adress address;
    @OneToOne(mappedBy = "office",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Location location;

    public Office(String officeName,String city,
                  String district,
                  String street,
                  String number){ {
        this.officeName = officeName;
        this.address = new Adress(city,district,street,number);

    }

    }

}
