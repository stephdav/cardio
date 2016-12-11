package com.sopra.agile.cardio.back.model;

public class RestError {

	private String code;
	private String message;
	private String description;

	public RestError() {
		// Empty constructor
	}

	public RestError(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public RestError(String code, String message, String description) {
		this(code, message);
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
