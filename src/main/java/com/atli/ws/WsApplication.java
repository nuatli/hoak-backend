package com.atli.ws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.atli.ws.hoax.HoaxService;
import com.atli.ws.hoax.vm.HoaxSubmitVM;
import com.atli.ws.user.User;
import com.atli.ws.user.UserService;

@SpringBootApplication
@EnableAutoConfiguration
public class WsApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(WsApplication.class, args);
		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
		System.out.println("********************* Bean Name Start *********************");
		for(String beanName : allBeanNames) {
			//System.out.println(beanName);
		}
		System.out.println("********************* Bean Name End *********************");
		
	}
	
	@Bean//Spring ile iliski kurduk, Uyguluama ayağı takltığı zaman çalışıyor.
	@Profile("!production")
	CommandLineRunner createInitialUsers(UserService userService,HoaxService hoaxService){
		return (args) -> {
			System.out.println("*************************** Initial User Created ***************************");
			for(int i =1 ;i<=25;i++){
				try {
					userService.getByUsername(String.format("user%s",i));
				}catch (Exception e) {
					User user = new User();
					user.setUsername("user"+i);
					user.setDisplayName("display"+i);
					user.setPassword("P4ssword");
					userService.save(user);
					for(int j =1 ;j<=20;j++){
						HoaxSubmitVM hoax = new HoaxSubmitVM();
						hoax.setContent("hoax ("+j+ ") from user ("+i+")");			
						hoaxService.save(hoax,user);
					}
				}
			}
			 

			
		};
	}

}
