package com.atli.ws.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class GenericResponse {
	private String message;


	public GenericResponse(String message) {
		super();
		this.message = message;
	}
}
