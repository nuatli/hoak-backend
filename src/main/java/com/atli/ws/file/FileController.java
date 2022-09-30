package com.atli.ws.file;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/0.0.1")
public class FileController {
	
	@Autowired
	FileService fileService;
 	
	
	@PostMapping("/hoax-attachments")
	Map<String,String> saveHoaxAttachment( MultipartFile file) {
		String fileName = fileService.saveHoaxAttachment(file);
		System.out.println(fileName);
		return Collections.singletonMap("name", fileName);
		
	}

	/* Generic 
		
		@PostMapping("/hoax-attachments")
		Map<String,String> saveHoaxAttachment(@RequestParam(name="image") MultipartFile multipartFile) {
			String fileName = fileService.saveHoaxAttachment(multipartFile);
			System.out.println(fileName);
			return Collections.singletonMap("name", fileName);
			
		}
	 */
}
