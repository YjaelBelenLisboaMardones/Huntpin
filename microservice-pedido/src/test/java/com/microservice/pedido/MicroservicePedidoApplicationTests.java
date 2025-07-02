package com.microservice.pedido;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroservicePedidoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainRunsWithoutStartingWebServer() {
		System.setProperty("spring.main.web-application-type", "none");
		MicroservicePedidoApplication.main(new String[]{});
}

}
