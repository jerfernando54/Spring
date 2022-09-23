package com.boot.bookingrestaurantapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.TurnRest;

@Service
public interface TurnService {

	TurnRest getTurn(Long turnId) throws BookingException;

	String createTurn(TurnRest turn) throws BookingException;

	String deleteTurn(Long turnId) throws BookingException;
	
	List<TurnRest> getTurns() throws BookingException;

}
