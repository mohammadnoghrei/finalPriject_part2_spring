package com.example.mohammad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "orders")
public class Order  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    private Customer customer;
    @ManyToOne
    private Expert expert;
    @ManyToOne
    @NotNull
    private SubServices subServices;
    private double customerOfferPrice;
    private double expertOfferPrice;
    private double finalPrice;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @FutureOrPresent
    private LocalDate orderRegisterDate;
    @FutureOrPresent
    private LocalDate requestedDateToDoOrder;
    @FutureOrPresent
    private LocalDate toDoOrderDate;
    private boolean doOrder;
    @Min(0)@Max(5)
    private int rate;
    private String description;
    @OneToOne
    private Comment comment;
    private String address;



}