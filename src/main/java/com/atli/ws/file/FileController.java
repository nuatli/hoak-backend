package com.atli.ws.file;

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
	FileAttachment saveHoaxAttachment( MultipartFile file) {
		return fileService.saveHoaxAttachment(file);
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
	