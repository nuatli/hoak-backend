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
import com.atli.ws.user.User;

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
		File target = new File(appConfiguration.getProfileStoragePath()+"/"+fileName);
		OutputStream outputStream = new FileOutputStream(target);
		
		byte[] base64encoded = Base64.getDecoder().decode(image);
		
		outputStream.write(base64encoded);
		outputStream.close();
		return fileName;
	}
	
	public String generateRandomName(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public void deleteFile(String oldImageName,String path) {
		if(oldImageName == null) {
			return;
		}
		try {
			Files.deleteIfExists(Paths.get(path, oldImageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String detectType(byte[] arr) {
		return tika.detect(arr);
		/*
		byte[] base64encoded = Base64.getDecoder().decode(value);
		String fileType = tika.detect(base64encoded);
		return fileType;
		*/
	}
	
	public String detectType(String base64) {
		byte[] base64encoded = Base64.getDecoder().decode(base64);
		String fileType = tika.detect(base64encoded);
		return fileType;
	}

	public FileAttachment saveHoaxAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getAttachmentStoragePath()+"/"+fileName);
		String fileType = null;
		try {
			byte[] arr = multipartFile.getBytes();
			OutputStream outputStream = new FileOutputStream(target);
			outputStream.write(arr);
			outputStream.close();
			fileType = tika.detect(arr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileAttachment attachment = new FileAttachment();
		attachment.setName(fileName);
		attachment.setDate(new Date());
		attachment.setFileType(fileType);
		return fileAttachmentRepository.save(attachment);
	}
	
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void cleanupStorage() {
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
		List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndHoaxIsNull(twentyFourHoursAgo);
		for(FileAttachment file : filesToBeDeleted) {
			//delete file
			deleteFile(file.getName(),appConfiguration.getAttachmentStoragePath());
			//delete from table
			fileAttachmentRepository.deleteById(file.getId());
		}
	}

	public void deleteAllStoredFilesForUser(User inDB) {
		deleteFile(inDB.getImage(),appConfiguration.getProfileStoragePath());
		List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByHoaxUser(inDB);
		for(FileAttachment file : filesToBeRemoved) {
			deleteFile(file.getName(),appConfiguration.getAttachmentStoragePath());
			//fileAttachmentRepository.deleteById(file.getId());
		}
		
	}
}
