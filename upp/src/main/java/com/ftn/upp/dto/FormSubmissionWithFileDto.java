package com.ftn.upp.dto;

import java.util.List;

public class FormSubmissionWithFileDto {

	private List<FormSubmissionDto> form;
	private String file;
	private String fileName;
	
	public FormSubmissionWithFileDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FormSubmissionWithFileDto(List<FormSubmissionDto> form, String file, String fileName) {
		super();
		this.form = form;
		this.file = file;
		this.fileName = fileName;
	}
	
	public List<FormSubmissionDto> getForm() {
		return form;
	}
	public void setForm(List<FormSubmissionDto> form) {
		this.form = form;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
