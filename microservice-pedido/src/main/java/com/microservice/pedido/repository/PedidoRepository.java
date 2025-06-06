package com.microservice.pedido.repository;

import com.microservice.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer>{
    
    // Buscar todos los pedidos de un cliente específico
    List<Pedido> findByIdCliente(int idCliente);
}
