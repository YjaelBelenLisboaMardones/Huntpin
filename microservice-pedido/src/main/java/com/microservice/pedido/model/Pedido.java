package com.microservice.pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pedido")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaPedido;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    @Positive
    private Double costoTotal;

    private int idCliente;

    @Column(nullable = false)
    private String comentario;

    // Lista de productos relacionados (obtenidos desde otro microservicio)
    @Transient // No se almacena en la base de datos
    private List<Producto> productos;

}
