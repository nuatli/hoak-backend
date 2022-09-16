package com.atli.ws.shared;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.atli.ws.file.FileService;

public class ProfileImageValidator implements ConstraintValidator<ProfileImage, String>{

	@Autowired
	FileService fileService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String fileType = fileService.detectType(value);
		if(fileType.equalsIgnoreCase("image/jpeg") ||  fileType.equalsIgnoreCase("image/png")) {
			
		}
	}

}
