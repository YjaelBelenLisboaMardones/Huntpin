package com.microservice.pedido.dto;

//import com.microservice.pedido.model.Pedido;
//import java.util.List;
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
    private Integer id_producto;
    private String comentario;

}
