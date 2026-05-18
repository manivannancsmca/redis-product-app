package com.redis_product_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisProductAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisProductAppApplication.class, args);
	}

}
