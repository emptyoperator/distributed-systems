package org.leniv.distributed.systems.store;

import org.leniv.distributed.systems.store.entity.Product;
import org.leniv.distributed.systems.store.entity.User;
import org.leniv.distributed.systems.store.entity.User.Role;
import org.leniv.distributed.systems.store.repository.ProductRepository;
import org.leniv.distributed.systems.store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    CommandLineRunner dataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository) {
        return args -> {
            User customer = User.builder()
                    .username("customer")
                    .password(passwordEncoder.encode("customer"))
                    .build();
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.saveAll(List.of(customer, admin));
            Product laptop = Product.builder()
                    .name("Laptop")
                    .price(BigDecimal.valueOf(29_999))
                    .quantity(5)
                    .build();
            Product chair = Product.builder()
                    .name("Chair")
                    .price(BigDecimal.valueOf(3_999))
                    .quantity(15)
                    .build();
            Product watch = Product.builder()
                    .name("Watch")
                    .price(BigDecimal.valueOf(10_999))
                    .quantity(10)
                    .build();
            productRepository.saveAll(List.of(laptop, chair, watch));
        };
    }
}
