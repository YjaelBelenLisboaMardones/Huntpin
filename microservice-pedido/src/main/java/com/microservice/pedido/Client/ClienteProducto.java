package com.microservice.pedido.Client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="msvc-producto", url= "localhost:8090" )
public interface ClienteProducto {

}
