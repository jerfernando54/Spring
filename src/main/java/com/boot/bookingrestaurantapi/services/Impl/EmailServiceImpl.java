package com.boot.bookingrestaurantapi.services.Impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.dtos.EmailTemplateDto;
import com.boot.bookingrestaurantapi.entities.Notification;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.repositories.NotificationRepository;
import com.boot.bookingrestaurantapi.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	
	@Override
	public String processSendEmail(String receive, String templateCode, String currentName)
			throws BookingException {
		final EmailTemplateDto emailTemplateDto = findTemplateAndReplace(templateCode, currentName);
		
		this.sendEmail(receive, emailTemplateDto.getSubject(), emailTemplateDto.getTemplate());
		return "EMAIL_SENT";
	}
	
	private void sendEmail(String receive, final String subject, final String template) throws BookingException {
		final MimeMessage email = javaMailSender.createMimeMessage();
		final MimeMessageHelper content;
		
		try {
			content = new MimeMessageHelper(email, true);
			content.setSubject(subject);
			content.setTo(receive);
			content.setText(template, true);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		javaMailSender.send(email);
	}
	
	private EmailTemplateDto findTemplateAndReplace(final String templateCode, final String currentName) throws BookingException {
		
		final Notification notificationTemplate = notificationRepository.findByTemplateCode(templateCode)
				.orElseThrow(() -> new NotFoundException("TEMPLATE_NOT_FOUND", "TYPE_TEMPLATE__NOT_FOUND"));
		
		final EmailTemplateDto emailTemplateDto = new EmailTemplateDto();
		emailTemplateDto.setSubject(notificationTemplate.getTemplateType());
		emailTemplateDto.setTemplate(notificationTemplate.getTemplate().replaceAll("\\{"+"name"+"\\}", currentName));
		return emailTemplateDto;
	}
	

}
