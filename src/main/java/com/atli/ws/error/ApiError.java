package com.atli.ws.error;

import java.util.Date;
import java.util.Map;

import com.atli.ws.shared.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
	@JsonView(Views.Base.class)
	private int status;
	@JsonView(Views.Base.class)
	private String message;
	@JsonView(Views.Base.class)
	private String path;
	@JsonView(Views.Base.class)
	private long timestamp = new Date().getTime();
	@JsonView(Views.Base.class)
	private Map<String,String> validationErrors;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}
	public void setValidationErrors(Map<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}
	
	public ApiError(int Status,String Message,String Path){
		this.status = Status;
		this.message= Message;
		this.path = Path;
	}
	
	
}
