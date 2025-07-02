package com.microservice.producto.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.microservice.producto.controller.ProductoController;
import com.microservice.producto.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AssemblerProducto implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProductoById(producto.getId_producto())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProducts()).withRel("productos"));
    }
}