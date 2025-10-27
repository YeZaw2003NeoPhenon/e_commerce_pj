package com.example.app_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUserServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner initUsers(UserDao userRepository) {
//		return args -> {
//			if (userRepository.count() == 0) { // avoid duplicate inserts on every restart
//				User admin = new User(
//						"Admin Master",
//						"admin@example.com",
//						"123",
//						Role.ADMIN
//				);
//
//				User user = new User(
//						"Normal User",
//						"user@example.com",
//						"user123",
//						Role.USER
//				);
//
//				userRepository.save(admin);
//				userRepository.save(user);
//
//				System.out.println("✅ Default users inserted ✅");
//			} else {
//				System.out.println("⚠ Users already exist, skipping seeding");
//			}
//		};
//	}
}
