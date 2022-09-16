package com.atli.ws.user.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserUpdateVM {
	@NotNull(message = "{hoaxify.constraint.displayname.NotNull.message}")
	@Size(min=4, max=255)
	private String displayName;

	private String image;
	

}
