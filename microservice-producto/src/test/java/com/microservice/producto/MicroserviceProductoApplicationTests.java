package com.microservice.producto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = MicroserviceProductoApplication.class)
class MicroserviceProductoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    void mainMethodRunsWithoutWebServer() {
        // Evita que se levante el servidor web durante el test
        System.setProperty("spring.main.web-application-type", "none");
        MicroserviceProductoApplication.main(new String[]{});
    }
	
}
