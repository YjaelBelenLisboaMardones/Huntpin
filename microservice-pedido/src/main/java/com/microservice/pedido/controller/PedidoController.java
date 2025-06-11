package com.microservice.pedido.controller;

import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.net.URI;
//import java.time.LocalDateTime;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/pedido")
public class PedidoController {


@Autowired
private PedidoService pedidoService;

@GetMapping("/listar")
public List<Pedido> getAllPedidos(){
    return pedidoService.findAll();
}
    
@GetMapping("/{id_pedido}")
public ResponseEntity<?> getProductoById(@PathVariable Integer id){
    Optional<Pedido> pedido = pedidoService.getPedidoById(id);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
    }   else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
    }
}
  
@PostMapping
public ResponseEntity<?> save(@Valid @RequestBody Pedido pedido){
    try{
        Pedido pedidoGuardado = pedidoService.save(pedido);

            //Uri del nuevo recurso creado ( ej: http://localhost:8080/paciente/5)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedidoGuardado.getIdPedido())
                .toUri();

            //Respuesta exitosa con cabeceras y cuerpo
            return ResponseEntity
                .created(location)//Código 201 Created
                .body(pedidoGuardado);
    } catch(DataIntegrityViolationException e){
            //Ejemplo: Error si hay un campo único duplicado (ej: email repetido)
        Map<String,String> error = new HashMap<>();
        error.put("message","El pedido ya está registrado");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//Error 409
        }
    }
 
    @PutMapping("/{id_pedido}")
    public ResponseEntity<Pedido> update(@PathVariable int id_pedido,@RequestBody Pedido pedido){
        try{

            Pedido ped = pedidoService.getPedidoById2(id_pedido);
            ped.setIdPedido(id_pedido);
            ped.setFechaPedido(pedido.getFechaPedido());
            ped.setEstado(pedido.getEstado());
            ped.setCostoTotal(pedido.getCostoTotal());
            ped.setId_producto(pedido.getId_producto());

            pedidoService.save(pedido);
            return ResponseEntity.ok(pedido);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id){
        try{

            pedidoService.delete(id);
            return ResponseEntity.noContent().build();//Operacion exitosa pero no hay
            //contenido para devolver

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

}
