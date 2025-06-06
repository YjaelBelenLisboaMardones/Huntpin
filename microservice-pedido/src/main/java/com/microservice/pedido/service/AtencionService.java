package com.microservice.pedido.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.pedido.dto.AtencionDTO;
import com.microservice.pedido.model.Atencion;
import com.microservice.pedido.repository.AtencionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    public boolean existeAtencion(int id){
        return atencionRepository.existsById(id);
    }

    public List<Atencion> findAll(){
        return atencionRepository.findAll();
    }

    public Optional<Atencion> getPatientById(int id){
        return atencionRepository.findById(id);
    }

    public Atencion getPatientById2(int id){
        return atencionRepository.findById(id).get();
    }

    //La funcion save funciona tanto como para crear o actualizar
    
}
