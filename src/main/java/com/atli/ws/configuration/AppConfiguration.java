package com.atli.ws.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app") // applicationPropertiesdeki verileri direk alıyor ve yml
public class AppConfiguration {
		private String uploadPath;
		private int delete_file_scheduling;
		
		private String profileStorage = "profile";
		private String attachmentStorage = "attachments";
		
		public String getProfileStoragePath() {
			return uploadPath + "/" + profileStorage;
		}
		
		public String getAttachmentStoragePath() {
			return uploadPath + "/" + attachmentStorage;
		}
		

}