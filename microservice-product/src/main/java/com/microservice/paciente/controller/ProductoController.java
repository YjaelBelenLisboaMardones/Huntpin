package com.microservice.producto.controller;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.ProductoService;
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
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> getAllProducts(){
        return productoService.findAll();
    }
    
    @GetMapping("/{id_producto}")
    public ResponseEntity<?> getProductoById(@PathVariable Integer id){
        Optional<Producto> producto = productoService.getProductoById(id);
/*  
        if(producto.isPresent()){
            //Respuesta exitosa con cabeceras personalizadas (opcional)
            return ResponseEntity.ok()
                    .header("mi-encabezado", "valor")
                    .body(producto.get());
        } else{
            //Respuesta de error con cuerpo personalizado ( ej: JSON con mensaje)
            Map<String,String> errorBody = new HashMap<>();
            errorBody.put("message","No se encontró el paciente con ID: " + id);
            errorBody.put("status","404");
            errorBody.put("timestamp",LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorBody);
        }
 */
        //Se usa <?> para permitir que el método retorne:
        //1. Un objeto Paciente ( en caso de éxito, código 200)
        //2. Un objeto de error personalizado(en caso de fallo, código 404)

        //Al usar <?>, no estas limitando el cuerpo de la respuesta a un único tipo (como Paciente)
        //Si no que permites múltiples tipos en la respuesta

    }
    

    @PostMapping("path")
    public ResponseEntity<?> save(@Valid @RequestBody Producto producto){
        try{
            Producto productoGuardado = productoService.save(producto);

            //Uri del nuevo recurso creado ( ej: http://localhost:8080/paciente/5)
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productoGuardado.getId_producto())
                    .toUri();

            //Respuesta exitosa con cabeceras y cuerpo
            return ResponseEntity
                    .created(location)//Código 201 Created
                    .body(productoGuardado);
        } catch(DataIntegrityViolationException e){
            //Ejemplo: Error si hay un campo único duplicado (ej: email repetido)
            Map<String,String> error = new HashMap<>();
            error.put("message","El email ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//Error 409
        }
    }
 
    @PutMapping("/{id_producto}")
    public ResponseEntity<Producto> update(@PathVariable int id,@RequestBody Producto producto){
        try{

            Producto prod = productoService.getProductById2(id);
            prod.setId_paciente(id);
            prod.setRut(producto.getRut());
            prod.setNombres(producto.getNombres());
            prod.setApellidos(producto.getApellidos());
            prod.setFechaNacimiento(producto.getFechaNacimiento());
            prod.setCorreo(producto.getCorreo());

            productoService.save(producto);
            return ResponseEntity.ok(producto);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id){
        try{

            productoService.delete(id);
            return ResponseEntity.noContent().build();//Operacion exitosa pero no hay
            //contenido para devolver

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }

}
