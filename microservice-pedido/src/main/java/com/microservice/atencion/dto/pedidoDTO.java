package com.microservice.pedido.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PedidoDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaPedido;
    private String estado;
    private Double costoTotal;
    private Integer idCliente;
    private String comentario;
    // Lista de productos relacionados (obtenidos desde otro microservicio)
    private List<ProductoDTO> productos;
}
