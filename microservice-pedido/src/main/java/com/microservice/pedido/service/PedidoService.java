package com.microservice.pedido.service;

import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public boolean existePedido(int id){
        return pedidoRepository.existsById(id);
    }

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> getPedidoById(int id){
        return pedidoRepository.findById(id);
    }

    public Pedido getPedidoById2(int id){
        return pedidoRepository.findById(id).get();
    }

    //La funcion save funciona tanto como para crear o actualizar
    public Pedido save(Pedido pedido){
    return pedidoRepository.save(pedido);
    }

    public void delete(int id){
        pedidoRepository.deleteById(id);
    }
}
