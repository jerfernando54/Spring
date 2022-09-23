package com.boot.bookingrestaurantapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "TEMPLATE")
	private String template;
	
	@Column(name = "TEMPLATE_CODE")
	private String templateCode;

	public Long getId() {
		return id;
	}

	public String getTemplate() {
		return template;
	}

	public String getTemplateType() {
		return templateCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setTemplateType(String templateCode) {
		this.templateCode = templateCode;
	}
}
