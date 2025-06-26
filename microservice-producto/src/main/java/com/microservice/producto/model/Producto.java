package com.microservice.producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;


@Entity//Le indico con el decorador que este sera el id de mi tabla
@Table(name = "producto")//Con esto le digo que sera autoincrementable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;

    @Column(name="nombre", unique=true,length=100,nullable = false)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(name = "stock",nullable = false)
    private int stock;

}
