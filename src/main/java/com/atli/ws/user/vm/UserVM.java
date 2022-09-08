package com.atli.ws.user.vm;

import com.atli.ws.user.User;

import lombok.Data;

@Data
public class UserVM {
	private String username;
	private String displayName;
	private String image;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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
	
	public UserVM(User user){
		this.username = user.getUsername();
		this.displayName = user.getDisplayName();
		this.image = user.getImage();
	}
	
}
