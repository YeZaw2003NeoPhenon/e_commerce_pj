package com.example.category_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(CategoryDao categoryDao){
//		return args -> {
//			categoryDao.saveAll(Arrays.asList(new Category("Tech Accessories","Laptop"),
//											  new Category("Mechanic","screw")));
//		};
//	}

}
