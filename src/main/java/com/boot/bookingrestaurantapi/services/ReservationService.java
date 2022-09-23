package com.boot.bookingrestaurantapi.services;

import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;

@Service
public interface ReservationService {

	ReservationRest getReservation(Long reservationId) throws BookingException;
	
	ReservationRest getReservationByLocator(String locator) throws BookingException;
	
	String createReservation(CreateReservationRest createReservationRest) throws BookingException;
	
	String deleteReservation(String locator) throws BookingException;
	
	
}
