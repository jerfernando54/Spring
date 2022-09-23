package com.boot.bookingrestaurantapi.dtos;

public class EmailTemplateDto {
	
	private Long id;
	private String template;
	private String subject;
	private String templateCode;
	
	
	public Long getId() {
		return id;
	}
	public String getTemplate() {
		return template;
	}
	public String getSubject() {
		return subject;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

}
