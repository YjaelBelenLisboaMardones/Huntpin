package com.microservice.pedido;

import com.microservice.pedido.model.*;
import com.microservice.pedido.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PedidoRepository pedidoRepository;
   

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar tipos de sala
         for (int i = 0; i < 10; i++) {
            Pedido pedido = new Pedido();
            pedido.setFechaPedido(LocalDate.now().minusDays(random.nextInt(30)));
            pedido.setEstado(faker.options().option("PENDIENTE", "ENVIADO", "ENTREGADO"));
            pedido.setCostoTotal(faker.number().randomDouble(2, 10, 500));
            pedido.setId_producto(random.nextInt(10) + 1); // IDs de producto de ejemplo
            pedido.setComentario(faker.lorem().sentence());

            pedidoRepository.save(pedido);
        }
    }
}