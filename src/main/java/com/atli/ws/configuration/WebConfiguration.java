package com.atli.ws.configuration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	
	@Autowired
	AppConfiguration appConfiguration;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//http://{url}:{port}/images/profile.png ->  images diye aratarak pictureStorage'a yönlendirme veriliyor
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:./"+appConfiguration.getUploadPath()+"/")
		//.addResourceLocations("file:./picture-storage/")
		.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
		;
	}
	
	@Bean
	CommandLineRunner createStorageDirectories(){
		return(args) -> {
			createFolder(appConfiguration.getUploadPath());
			createFolder(appConfiguration.getAttachmentStoragePath());
			createFolder(appConfiguration.getProfileStoragePath());
		};
	}

	private void createFolder(String path) {
		File folder = new File(path);
		if(!(folder.exists() && folder.isDirectory())){
			folder.mkdir();
		}
	}
}
