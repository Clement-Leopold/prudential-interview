package com.prudential.interview.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;


/**
 * @author clement
 */
@Setter
@Getter
@ToString
@Entity
@Table(name = "t_car_rent")
@NoArgsConstructor
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_id")
    private Integer autoId;

    @Column(name = "id")
    private String id;

    /**
     * who reserve the car
     */
    @Column(name = "username")
    private  String username;
    /**
     * which car model be reserved
     */
    @Column(name = "car_model")
    private  Integer carModel;
    /**
     * reserve time; unit is in second, convenient for computing.
     */
    @Column(name = "reserve_seconds")
    private  Integer reserveSeconds;

    @Column(name = "returned")
    private Integer returned;

    public CarRental(String username, Integer carModel, Integer reserveSeconds) {
        this.username = username;
        this.carModel = carModel;
        this.reserveSeconds = reserveSeconds;
        this.returned = 0;
        this.id = UUID.randomUUID().toString();
    }
}

