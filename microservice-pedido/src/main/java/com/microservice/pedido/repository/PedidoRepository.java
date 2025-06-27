package com.microservice.pedido.repository;

import com.microservice.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer>{
    
}
