package com.atli.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.atli.ws.user.User;
import com.atli.ws.user.UserService;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
		System.out.println("****************************************************************************************************************************");
	}
	
	/*
	@Bean //Spring ile iliski kurduk, Uyguluama ayağı takltığı zaman çalışıyor.
	CommandLineRunner createInitialUsers(UserService userService){
		return new CommandLineRunner(){
			@Override
			public void run(String... args) throws Exception {
				System.out.println("*************************** Initial User Created ***************************");
				User user = new User();
				user.setUsername("user1");
				user.setDisplayName("display1");
				user.setPassword("P4ssword");
				userService.save(user);
			}
		};
	}
	*/
	@Bean
	CommandLineRunner createInitialUsers(UserService userService){
		return (args) -> {
			System.out.println("*************************** Initial User Created ***************************");
			/*
			User user = new User();
			user.setUsername("user1");
			user.setDisplayName("display1");
			user.setPassword("P4ssword");
			userService.save(user);
			*/
		};
	}

}
