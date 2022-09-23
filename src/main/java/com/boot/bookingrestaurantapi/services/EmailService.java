package com.boot.bookingrestaurantapi.services;

import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.exceptions.BookingException;

@Service
public interface EmailService {

	public String processSendEmail(final String receiver, final String templateCode,
			String currentName) throws BookingException;

}
