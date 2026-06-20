package com.example.isdemir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication // com.example.isdemir.* altını otomatik tarar
@EnableJpaRepositories(basePackages = {
        "com.example.isdemir.user",
        "com.example.isdemir.repository" // diğer JPA repo paketlerin
})
@EntityScan(basePackages = "com.example.isdemir.model") // Entity'ler (User vs.)
public class IsdemirApplication {
    public static void main(String[] args) {
        SpringApplication.run(IsdemirApplication.class, args);
    }
}
