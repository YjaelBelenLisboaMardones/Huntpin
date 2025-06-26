package com.microservice.producto;

import com.microservice.producto.model.*;
import com.microservice.producto.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


import java.util.Random;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

   for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName() + "_" + i); // Para evitar duplicados
            producto.setPrecio(faker.number().randomDouble(2, 10, 1000));
            producto.setStock(random.nextInt(100) + 1);

            productoRepository.save(producto);
        }
    }
}