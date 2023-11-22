package com.itb.mif3an.magictilogin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.itb.mif3an.magictilogin.model.Role;
import com.itb.mif3an.magictilogin.model.User;
import com.itb.mif3an.magictilogin.repository.RoleRepository;
import com.itb.mif3an.magictilogin.service.UserService;

@SpringBootApplication
public class MagicTiLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicTiLoginApplication.class, args);
		
		// Utilizamos o construtor para criar nossos objetos
		
	  //User user = new User();
	 // User user2 = new User("rogerio");
	 // User user3 = new User("rogerio", "Caetano");
	   
	}
	
	// Rotina que será executada no momento do start da aplicação!
	
	@Bean
	CommandLineRunner run(UserService userService, RoleRepository roleRepository) {
		return args -> {
			
			if(roleRepository.findAll().size() == 0) {
				userService.saveRole(new Role("ROLE_USER"));
				userService.saveRole(new Role("ROLE_ADMIN"));
				
			}
			
		};
	}

}
