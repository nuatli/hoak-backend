package com.atli.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.atli.ws.user.UserService;

@SpringBootApplication
@EnableAutoConfiguration
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
		System.out.println("****************************************************************************************************************************");
	}
	
	@Bean//Spring ile iliski kurduk, Uyguluama ayağı takltığı zaman çalışıyor.
	@Profile("!production")
	CommandLineRunner createInitialUsers(UserService userService){
		return (args) -> {
			System.out.println("*************************** Initial User Created ***************************");
			/*
			for(int i =1 ;i<=10;i++){
				User user = new User();
				user.setUsername("user"+i);
				user.setDisplayName("display"+i);
				user.setPassword("P4ssword");
				userService.save(user);
			}
			*/
		};
	}

}
