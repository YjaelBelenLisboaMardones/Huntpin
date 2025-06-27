package com.microservice.producto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.microservice.producto.model.Producto;
import com.microservice.producto.repository.ProductoRepository;
import com.microservice.producto.service.ProductoService;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

public class ProductoServiceTest {

    
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
        void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());
        List<Producto> productos = productoService.findAll();
        assertNotNull(productos);
        assertEquals(0, productos.size());
    }

    @Test
    void testGetProductoById() {
        Producto producto = new Producto();
        producto.setId_producto(1);
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        Optional<Producto> result = productoService.getProductoById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId_producto());
    }

    @Test
    void testSave() {
        Producto producto = new Producto();
        producto.setNombre("Test");
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto saved = productoService.save(producto);
        assertEquals("Test", saved.getNombre());
    }

    @Test
    void testDelete() {
        doNothing().when(productoRepository).deleteById(1);
        productoService.delete(1);
        verify(productoRepository, times(1)).deleteById(1);
    }

}
