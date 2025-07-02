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
	
}
