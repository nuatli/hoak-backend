package com.atli.ws.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.atli.ws.configuration.AppConfiguration;

@Service
@EnableScheduling
public class FileService {
	AppConfiguration appConfiguration;
	Tika tika;
	FileAttachmentRepository fileAttachmentRepository;
	
	public FileService(AppConfiguration appConfiguration,FileAttachmentRepository fileAttachmentRepository) {
		super();
		this.appConfiguration = appConfiguration;
		this.tika = new Tika();
		this.fileAttachmentRepository = fileAttachmentRepository;
	}
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);
	
	public String writeBase64EncodedStringToFile(String image,String username) throws IOException {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getUploadPath()+"/"+fileName);
		OutputStream outputStream = new FileOutputStream(target);
		
		byte[] base64encoded = Base64.getDecoder().decode(image);
		
		outputStream.write(base64encoded);
		outputStream.close();
		return fileName;
	}
	
	
	public String generateRandomName(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}


	public void deleteFile(String oldImageName) {
		if(oldImageName == null) {
			return;
		}
		try {
			Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String detectType(String value) {
		byte[] base64encoded = Base64.getDecoder().decode(value);
		String fileType = tika.detect(base64encoded);
		return fileType;
	}

	public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getUploadPath()+"/"+fileName);
		try {
			OutputStream outputStream = new FileOutputStream(target);
			outputStream.write(multipartFile.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileAttachment attachment = new FileAttachment();
		attachment.setName(fileName);
		attachment.setDate(new Date());
		return fileAttachmentRepository.save(attachment);
	}
	
	
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void cleanupStorage() {
		//System.out.println(appConfiguration.getDelete_file_scheduling());
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
		List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);
		for(FileAttachment file : filesToBeDeleted) {
			//delete file
			deleteFile(file.getName());
			//delete from table
			fileAttachmentRepository.deleteById(file.getId());
		}
	}
}
