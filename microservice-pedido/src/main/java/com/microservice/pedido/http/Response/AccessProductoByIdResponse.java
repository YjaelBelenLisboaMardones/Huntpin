package com.microservice.pedido.http.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessProductoByIdResponse {

    private String nombre;
    private double precio;
    private int stock;

}
