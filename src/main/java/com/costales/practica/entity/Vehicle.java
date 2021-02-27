package com.costales.practica.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class Vehicle {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "brand", nullable = false, unique = true)
    private String brand;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "model", nullable = false, unique = true)
    private String model;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "color", nullable = false, unique = true)
    private String color;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "manufacturingDate", nullable = false, unique = true)
    private LocalDate manufacturingDate;



}
