package com.microservice.producto;

import com.microservice.producto.controller.ProductoController;
import com.microservice.producto.model.Producto;
import com.microservice.producto.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void testGetAllProducts() {
        when(productoService.findAll()).thenReturn(Collections.emptyList());
        List<Producto> result = productoController.getAllProducts();
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productoService, times(1)).findAll();
    }
  @Test
    void testGetProductoById_found() {
        Producto producto = new Producto();
        producto.setId_producto(1);
        when(productoService.getProductoById(1)).thenReturn(Optional.of(producto));

        ResponseEntity<?> response = productoController.getProductoById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testGetProductoById_notFound() {
        when(productoService.getProductoById(2)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productoController.getProductoById(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Producto no encontrado", response.getBody());
    }

    @Test
    void testSave_success() {
        Producto producto = new Producto();
        producto.setId_producto(1);

        when(productoService.save(any(Producto.class))).thenReturn(producto);

        ResponseEntity<?> response = productoController.save(producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(producto, response.getBody());
        var location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().contains("/1"));
    }

 @Test
void testSave_conflict() {
    Producto producto = new Producto();
    when(productoService.save(any(Producto.class))).thenThrow(new DataIntegrityViolationException("Duplicado"));

    ResponseEntity<?> response = productoController.save(producto);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertInstanceOf(Map.class, response.getBody());
    Map<?,?> error = (Map<?,?>) response.getBody();
    assertNotNull(error, "El body no debe ser null");
    assertEquals("El producto ya está registrado", error.get("message"));
}

    @Test
    void testUpdate_success() {
        Producto producto = new Producto();
        producto.setNombre("Nuevo");
        producto.setPrecio(10.0);
        producto.setStock(5);

        Producto prodExistente = new Producto();
        prodExistente.setId_producto(1);

        when(productoService.getProductById2(1)).thenReturn(prodExistente);
        when(productoService.save(any(Producto.class))).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.update(1, producto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testUpdate_notFound() {
        Producto producto = new Producto();
        when(productoService.getProductById2(99)).thenThrow(new RuntimeException());
        ResponseEntity<Producto> response = productoController.update(99, producto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testEliminar_success() {
        doNothing().when(productoService).delete(1);
        ResponseEntity<?> response = productoController.eliminar(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testEliminar_notFound() {
        doThrow(new RuntimeException()).when(productoService).delete(99);
        ResponseEntity<?> response = productoController.eliminar(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
