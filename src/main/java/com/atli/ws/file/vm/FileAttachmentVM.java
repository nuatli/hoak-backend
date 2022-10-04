package com.atli.ws.file.vm;

import com.atli.ws.file.FileAttachment;

import lombok.Data;

@Data
public class FileAttachmentVM {
	 private String name;
	 
	 public FileAttachmentVM(FileAttachment fileAttachment) {
		 this.setName(fileAttachment.getName());
	 }
}
