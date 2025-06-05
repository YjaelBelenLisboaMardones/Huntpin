package com.microservice.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private int id_producto;
    private String nombre;
    private String marca;
    private double precio;
    private int stock;
}
