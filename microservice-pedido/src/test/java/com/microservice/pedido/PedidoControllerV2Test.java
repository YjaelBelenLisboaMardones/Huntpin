package com.microservice.pedido;
import com.microservice.pedido.controller.PedidoControllerV2;
import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.service.PedidoService;
import com.microservice.pedido.assembler.AssemblerPedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoControllerV2Test {

    @Mock
    private PedidoService pedidoService;

    @Mock
    private AssemblerPedido assembler;

    @InjectMocks
    private PedidoControllerV2 pedidoControllerV2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPedidos() {
        Pedido pedido = new Pedido();
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoService.findAll()).thenReturn(pedidos);
        EntityModel<Pedido> entityModel = EntityModel.of(pedido);
        when(assembler.toModel(pedido)).thenReturn(entityModel);

        CollectionModel<EntityModel<Pedido>> result = pedidoControllerV2.getAllPedidos();

        assertNotNull(result);
        assertTrue(result.getContent().contains(entityModel));
        verify(pedidoService, times(1)).findAll();
    }

    @Test
    void testGetPedidoById_found() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        when(pedidoService.getPedidoById(1)).thenReturn(Optional.of(pedido));
        EntityModel<Pedido> entityModel = EntityModel.of(pedido);
        when(assembler.toModel(pedido)).thenReturn(entityModel);

        EntityModel<Pedido> result = pedidoControllerV2.getPedidoById(1);

        assertNotNull(result);
        assertEquals(entityModel, result);
        verify(pedidoService, times(1)).getPedidoById(1);
    }

    @Test
    void testGetPedidoById_notFound() {
        when(pedidoService.getPedidoById(99)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoControllerV2.getPedidoById(99);
        });
        assertEquals("Pedido no encontrado", exception.getMessage());
    }

    @Test
    void testCreatePedido() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);
        EntityModel<Pedido> entityModel = EntityModel.of(pedido);
        when(assembler.toModel(pedido)).thenReturn(entityModel);

        ResponseEntity<EntityModel<Pedido>> response = pedidoControllerV2.createPedido(pedido);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
    }

    @Test
    void testUpdatePedido() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);
        EntityModel<Pedido> entityModel = EntityModel.of(pedido);
        when(assembler.toModel(pedido)).thenReturn(entityModel);

        ResponseEntity<EntityModel<Pedido>> response = pedidoControllerV2.updatePedido(1, pedido);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
    }

    @Test
    void testDeletePedido() {
        doNothing().when(pedidoService).delete(1);

        ResponseEntity<?> response = pedidoControllerV2.deletePedido(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pedidoService, times(1)).delete(1);
    }
}