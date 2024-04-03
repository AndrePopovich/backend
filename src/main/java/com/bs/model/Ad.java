package com.bs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "ads_table")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String descriptions;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private BigDecimal price;
    private byte[] photos;
    private boolean isActive;
    private LocalDate dateOfCreate;
    private int views;
}
