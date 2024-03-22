package com.mng.Mng.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locations")
@Entity
public  class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double latitude;
    private double longitude;
    private double longitudeDelta;
    private double latitudeDelta;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;

    public Location(double latitude, double longitude, double longitudeDelta, double latitudeDelta) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.longitudeDelta = longitudeDelta;
        this.latitudeDelta = latitudeDelta;
    }

    public Location(double latitude, double longitude, double longitudeDelta, double latitudeDelta, User user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.longitudeDelta = longitudeDelta;
        this.latitudeDelta = latitudeDelta;
        this.user = user;
    }

    public Location(double latitude, double longitude, double longitudeDelta, double latitudeDelta, Office office) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.longitudeDelta = longitudeDelta;
        this.latitudeDelta = latitudeDelta;
        this.office = office;
    }

}
