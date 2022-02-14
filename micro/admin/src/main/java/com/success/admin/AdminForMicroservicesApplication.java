package com.success.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class AdminForMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminForMicroservicesApplication.class, args);
	}

}
