package com.microservice.pedido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.pedido.model.Pedido;
import com.microservice.pedido.repository.PedidoRepository;
import com.microservice.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());
        List<Pedido> pedidos = pedidoService.findAll();
        assertNotNull(pedidos);
        assertEquals(0, pedidos.size());
    }

    @Test
    void testGetPedidoById() {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        Optional<Pedido> result = pedidoService.getPedidoById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getIdPedido());
    }

    @Test
    void testSave() {
        Pedido pedido = new Pedido();
        pedido.setEstado("PENDIENTE");
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        Pedido saved = pedidoService.save(pedido);
        assertEquals("PENDIENTE", saved.getEstado());
    }

    @Test
    void testDelete() {
        doNothing().when(pedidoRepository).deleteById(1);
        pedidoService.delete(1);
        verify(pedidoRepository, times(1)).deleteById(1);
    }

    @Test
    void testExistePedido() {
        when(pedidoRepository.existsById(1)).thenReturn(true);
        boolean existe = pedidoService.existePedido(1);
        assertTrue(existe);
    }
}