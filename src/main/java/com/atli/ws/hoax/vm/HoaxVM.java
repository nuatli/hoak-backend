package com.atli.ws.hoax.vm;

import com.atli.ws.file.vm.FileAttachmentVM;
import com.atli.ws.hoax.Hoax;
import com.atli.ws.user.vm.UserVM;

import lombok.Data;

@Data
public class HoaxVM {
	private long id;
	private String content;
	private long timestamp;
	private UserVM user;
	private FileAttachmentVM fileAttachment;
	
	public HoaxVM(Hoax hoax) {
		this.setId(hoax.getId());
		this.setContent(hoax.getContent());
		this.setTimestamp(hoax.getTimestamp().getTime());
		this.setUser(new UserVM(hoax.getUser()));
		if(hoax.getFileAttachment() != null) {
			this.fileAttachment = new FileAttachmentVM(hoax.getFileAttachment());
		}
		
	}
}	
