package com.microservice.producto.repository;

import com.microservice.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer>{
    //Metodos heredados aqui
}
