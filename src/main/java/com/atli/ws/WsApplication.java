package com.atli.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.atli.ws.hoax.Hoax;
import com.atli.ws.hoax.HoaxService;
import com.atli.ws.user.User;
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
	CommandLineRunner createInitialUsers(UserService userService,HoaxService hoaxService){
		return (args) -> {
			System.out.println("*************************** Initial User Created ***************************");
			
			for(int i =1 ;i<=25;i++){
				User user = new User();
				user.setUsername("user"+i);
				user.setDisplayName("display"+i);
				user.setPassword("P4ssword");
				userService.save(user);
				for(int j =1 ;j<=2;j++){
					Hoax hoax = new Hoax();
					hoax.setContent("hoax ("+j+ ") from user ("+i+")");			
					hoaxService.save(hoax,user);
				}
			}
			 

			
		};
	}

}
