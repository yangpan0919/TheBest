package com.best.zcdn;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(value = "com.best.zcdn.mapper")
//@EnableCaching
@SpringBootApplication
public class TobestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TobestApplication.class, args);
	}
}
