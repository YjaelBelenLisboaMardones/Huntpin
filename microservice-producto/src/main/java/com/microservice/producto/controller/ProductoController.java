package com.microservice.producto.controller;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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

@RestController
@RequestMapping("/api/producto")
@Tag(name = "Producto", description = "Operaciones relacionadas con los productos")
public class ProductoController {

@Autowired
private ProductoService productoService;

@GetMapping("/listar")
@Operation(summary="Obtener todos los productos", description = "Obtiene una lista de todos los productos")
public List<Producto> getAllProducts(){
    return productoService.findAll();
}
    
@GetMapping("/{id_producto}")
public ResponseEntity<?> getProductoById(@PathVariable("id_producto") Integer id_producto) {
    Optional<Producto> producto = productoService.getProductoById(id_producto);
    if (producto.isPresent()) {
        return ResponseEntity.ok(producto.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }
}
  
@PostMapping
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
        error.put("message","El producto ya está registrado");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//Error 409
        }
    }
 

    @PutMapping("/{id_producto}")
    @Operation(summary="Actualizar un producto", description = "Actualiza los detalles de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
})
    public ResponseEntity<Producto> update(@PathVariable int id_producto,@RequestBody Producto producto){
        try{

            Producto prod = productoService.getProductById2(id_producto);
            prod.setId_producto(id_producto);
            prod.setNombre(producto.getNombre());
            prod.setPrecio(producto.getPrecio());
            prod.setStock(producto.getStock());

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