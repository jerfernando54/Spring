package com.boot.bookingrestaurantapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.TurnService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/booking-restaurant/" + "v1")
public class TurnController {

	@Autowired
	TurnService turnService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "turn" + "/{" + "turnId"
			+ "}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<TurnRest> getTurnById(@PathVariable Long turnId) throws BookingException {
		return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK", turnService.getTurn(turnId));
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "turns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<List<TurnRest>> getTurns() throws BookingException {
		return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK", turnService.getTurns());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "turn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<String> createTurn(@RequestBody TurnRest turn) throws BookingException {
		return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
				turnService.createTurn(turn));
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "deleteTurn", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<String> deleteTurn(@RequestParam Long turnId) throws BookingException {
		return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "OK", turnService.deleteTurn(turnId));
	}

}
