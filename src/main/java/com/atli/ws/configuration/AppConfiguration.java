package com.atli.ws.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app") // applicationPropertiesdeki verileri direk alıyor ve yml
public class AppConfiguration {
		private String uploadPath;

}