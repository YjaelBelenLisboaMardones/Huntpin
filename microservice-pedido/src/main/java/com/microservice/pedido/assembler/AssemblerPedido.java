package com.microservice.pedido.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.microservice.pedido.controller.PedidoController;
import com.microservice.pedido.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AssemblerPedido implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    @NonNull
    public EntityModel<Pedido> toModel(@NonNull Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).getProductoById(pedido.getIdPedido())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).getAllPedidos()).withRel("pedidos"));
    }
}