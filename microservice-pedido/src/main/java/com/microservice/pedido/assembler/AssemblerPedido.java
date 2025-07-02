package com.microservice.pedido.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.microservice.pedido.controller.PedidoController;
import com.microservice.pedido.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AssemblerPedido implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).getProductoById(pedido.getIdPedido())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).getAllPedidos()).withRel("pedidos"));
    }
}