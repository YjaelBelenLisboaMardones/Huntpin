package com.microservice.producto.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.microservice.producto.controller.ProductoController;
import com.microservice.producto.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AssemblerProducto implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    @NonNull
    public EntityModel<Producto> toModel(@NonNull Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProductoById(producto.getId_producto())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProducts()).withRel("productos"));
    }
}