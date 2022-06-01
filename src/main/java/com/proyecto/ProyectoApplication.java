package com.proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
public class ProyectoApplication implements CommandLineRunner {

	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password="12345";
		String passwordBcrypt=passwordEncoder.encode(password);
		System.out.println(passwordBcrypt);
	}
}
