package com.boot.bookingrestaurantapi.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.TurnService;

@Service
public class TurnServiceImpl implements TurnService {

	@Autowired
	TurnRepository turnRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;

	public static final ModelMapper modelmapper = new ModelMapper();
	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	@Override
	public TurnRest getTurn(Long turnId) throws BookingException {
		return modelmapper.map(getTurnEntity(turnId), TurnRest.class);
	}

	@Override
	public List<TurnRest> getTurns() throws BookingException {
		final List<Turn> turnEntities = turnRepository.findAll();
		return turnEntities.stream().map(service -> modelmapper.map(service, TurnRest.class))
				.collect(Collectors.toList());
	}

	@Override
	public String createTurn(TurnRest turnRest) throws BookingException {
		final Turn turn = new Turn();
		
		turn.setName(turnRest.getName());
		turn.setRestaurant(getRestaurantEntity(turnRest.getRestaurantId()));;
		
		try {
			turnRepository.save(turn);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		return "CREATED_TURN";
	}

	@Override
	public String deleteTurn(Long turnId) throws BookingException {
		turnRepository.findById(turnId).orElseThrow(() -> new NotFoundException("TURN_NOT_FOUND", "TURN_NOT_FOUND"));

		try {
			turnRepository.deleteById(turnId);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		return "TURN_DELETED";
	}

	/* PRIVATE METHODS */

	private Turn getTurnEntity(Long turnId) throws BookingException {
		return turnRepository.findById(turnId)
				.orElseThrow(() -> new NotFoundException("TURN_NOT_FOUND", "TURN_NOT_FOUND"));
	}
	
	private Restaurant getRestaurantEntity(Long restaurantId) throws BookingException {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException("RESTAURANT_NOT_FOUND", "RESTAURANT_NOT_FOUND"));
		
	}

}
