package com.atli.ws.user.vm;

import javax.persistence.Lob;

import com.atli.ws.user.User;

import lombok.Data;

@Data
public class UserUpdateVM {
	private String displayName;
	@Lob
	private String image;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
