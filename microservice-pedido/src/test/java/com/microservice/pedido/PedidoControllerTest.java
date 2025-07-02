package com.microservice.pedido;

import com.microservice.pedido.controller.PedidoController;
import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PedidoControllerTest {


    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPedidos() {
        when(pedidoService.findAll()).thenReturn(Collections.emptyList());
        List<Pedido> result = pedidoController.getAllPedidos();
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(pedidoService, times(1)).findAll();
    }

    @Test
    void testGetPedidoById_found() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        when(pedidoService.getPedidoById(1)).thenReturn(Optional.of(pedido));

        ResponseEntity<?> response = pedidoController.getProductoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }
    @Test
    void testGetPedidoById_notFound() {
        when(pedidoService.getPedidoById(2)).thenReturn(Optional.empty());

        ResponseEntity<?> response = pedidoController.getProductoById(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Pedido no encontrado", response.getBody());
    }

      @Test
    void testSave_success() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);

        when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.save(pedido);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pedido, response.getBody());
        var location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().contains("/1"));
    }

    @Test
    void testSave_conflict() {
        Pedido pedido = new Pedido();
        when(pedidoService.save(any(Pedido.class))).thenThrow(new DataIntegrityViolationException("Duplicado"));

        ResponseEntity<?> response = pedidoController.save(pedido);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());
        Map<?,?> error = (Map<?,?>) response.getBody();
        assertNotNull(error, "El body no debe ser null");
        assertEquals("El pedido ya está registrado", error.get("message"));
    }

   @Test
    void testUpdate_success() {
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstado("NUEVO");
        pedido.setCostoTotal(100.0);
        pedido.setId_producto(5);

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setIdPedido(1);

        when(pedidoService.getPedidoById2(1)).thenReturn(pedidoExistente);
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.update(1, pedido);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

    @Test
    void testUpdate_notFound() {
        Pedido pedido = new Pedido();
        when(pedidoService.getPedidoById2(99)).thenThrow(new RuntimeException());
        ResponseEntity<Pedido> response = pedidoController.update(99, pedido);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testEliminar_success() {
        doNothing().when(pedidoService).delete(1);
        ResponseEntity<?> response = pedidoController.eliminar(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testEliminar_notFound() {
        doThrow(new RuntimeException()).when(pedidoService).delete(99);
        ResponseEntity<?> response = pedidoController.eliminar(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
}
