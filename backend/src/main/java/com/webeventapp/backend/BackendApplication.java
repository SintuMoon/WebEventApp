package com.webeventapp.backend;

import org.springframework.boot.SpringApplication;
import com.webeventapp.backend.config.AdminUserProperties;
import com.webeventapp.backend.config.ContactProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AdminUserProperties.class, ContactProperties.class})
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
