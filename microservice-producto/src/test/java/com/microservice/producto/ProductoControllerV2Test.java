package com.microservice.producto;

import com.microservice.producto.controller.ProductoControllerV2;
import com.microservice.producto.model.Producto;
import com.microservice.producto.service.ProductoService;
import com.microservice.producto.assembler.AssemblerProducto;

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

class ProductoControllerV2Test {

    @Mock
    private ProductoService productoService;

    @Mock
    private AssemblerProducto assembler;

    @InjectMocks
    private ProductoControllerV2 productoControllerV2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProductos() {
        Producto producto = new Producto();
        List<Producto> productos = List.of(producto);
        when(productoService.findAll()).thenReturn(productos);
        EntityModel<Producto> entityModel = EntityModel.of(producto);
        when(assembler.toModel(producto)).thenReturn(entityModel);

        CollectionModel<EntityModel<Producto>> result = productoControllerV2.getAllProductos();

        assertNotNull(result);
        assertTrue(result.getContent().contains(entityModel));
        verify(productoService, times(1)).findAll();
    }

    @Test
    void testGetProductoById_found() {
        Producto producto = new Producto();
        producto.setId_producto(1);
        when(productoService.getProductoById(1)).thenReturn(Optional.of(producto));
        EntityModel<Producto> entityModel = EntityModel.of(producto);
        when(assembler.toModel(producto)).thenReturn(entityModel);

        EntityModel<Producto> result = productoControllerV2.getProductoById(1);

        assertNotNull(result);
        assertEquals(entityModel, result);
        verify(productoService, times(1)).getProductoById(1);
    }

    @Test
    void testGetProductoById_notFound() {
        when(productoService.getProductoById(99)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoControllerV2.getProductoById(99);
        });
        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testCreateProducto() {
        Producto producto = new Producto();
        producto.setId_producto(1);
        when(productoService.save(any(Producto.class))).thenReturn(producto);
        EntityModel<Producto> entityModel = EntityModel.of(producto);
        when(assembler.toModel(producto)).thenReturn(entityModel);

        ResponseEntity<EntityModel<Producto>> response = productoControllerV2.createProducto(producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
    }

    @Test
    void testUpdateProducto() {
        Producto producto = new Producto();
        producto.setId_producto(1);
        when(productoService.save(any(Producto.class))).thenReturn(producto);
        EntityModel<Producto> entityModel = EntityModel.of(producto);
        when(assembler.toModel(producto)).thenReturn(entityModel);

        ResponseEntity<EntityModel<Producto>> response = productoControllerV2.updateProducto(1, producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(entityModel, response.getBody());
    }

    @Test
    void testDeleteProducto() {
        doNothing().when(productoService).delete(1);

        ResponseEntity<?> response = productoControllerV2.deleteProducto(1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(productoService, times(1)).delete(1);
    }
}