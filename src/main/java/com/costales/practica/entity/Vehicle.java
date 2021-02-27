package com.costales.practica.entity;

import com.costales.practica.validator.NotRepeat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle")
public class Vehicle {
    @Id()
    @NotNull
    @NotRepeat
    @Column(name = "vin", nullable = false)
    private long vin;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "brand", nullable = false)
    private String brand;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "model", nullable = false)
    private String model;
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "color", nullable = false)
    private String color;
}
