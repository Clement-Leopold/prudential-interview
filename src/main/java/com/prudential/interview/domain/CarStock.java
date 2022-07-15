package com.prudential.interview.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author clement
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_car_stock")
public class CarStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_id")
    private Integer autoId;

    @Column(name = "car_model")
    private Integer carModel;

    @Column(name = "stock")
    private Integer stock;
}
