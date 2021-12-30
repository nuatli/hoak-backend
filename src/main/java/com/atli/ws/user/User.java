package com.atli.ws.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{hoaxify.constraint.username.NotNull.message}")
	@Size(min=4, max=255)
	//@Column(unique=true)
	@UniqueUsername
	private String username;
	
	@NotNull(message = "{hoaxify.constraint.displayname.NotNull.message}")
	@Size(min=4, max=255)
	private String displayName;
	
	@NotNull
	//@Pattern(regexp = "^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})",message="{hoaxify.constraint.password.Pattern.message}")
	@Size(min=8, max=255)
	private String password;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
