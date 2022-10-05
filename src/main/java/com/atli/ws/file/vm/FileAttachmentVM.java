package com.atli.ws.file.vm;

import com.atli.ws.file.FileAttachment;

import lombok.Data;

@Data
public class FileAttachmentVM {
	 private String name;
	 private String fileType;
	 
	 public FileAttachmentVM(FileAttachment fileAttachment) {
		 this.setName(fileAttachment.getName());
		 this.setFileType(fileAttachment.getFileType());
	 }
}
