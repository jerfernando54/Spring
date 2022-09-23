package com.boot.bookingrestaurantapi.services.Impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.jsons.ReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.EmailService;
import com.boot.bookingrestaurantapi.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private TurnRepository turnRepository;

	@Autowired
	private EmailService emailService;

	public static final ModelMapper modelmapper = new ModelMapper();

	// GET RESERVATION BY ID METHOD
	public ReservationRest getReservation(Long reservationId) throws BookingException {
		return modelmapper.map(getReservationEntity(reservationId), ReservationRest.class);
	}

	public ReservationRest getReservationByLocator(String locator) throws BookingException {
		return modelmapper.map(getReservationEntityByLocator(locator), ReservationRest.class);
	}

	// CREATE RESERVATION METHOD
	@Override
	public String createReservation(CreateReservationRest createReservationRest) throws BookingException {

		final Restaurant restaurantId = restaurantRepository.findById(createReservationRest.getRestaurantId())
				.orElseThrow(() -> new NotFoundException("RESTAURANT_NOT_FOUND", "RESTAURANT_NOT_FOUND"));

		final Turn turn = turnRepository.findById(createReservationRest.getTurnId())
				.orElseThrow(() -> new NotFoundException("TURN_NOT_FOUND", "TURN_NOT_FOUND"));

		String locator = generateLocator(restaurantId, createReservationRest);

		final Reservation reservation = new Reservation();
		reservation.setLocator(locator);
		reservation.setPerson(createReservationRest.getPerson());
		reservation.setDate(createReservationRest.getDate());
		reservation.setRestaurant(restaurantId);
		reservation.setTurn(turn.getName());
		reservation.setEmail(createReservationRest.getEmail());
		reservation.setName(createReservationRest.getName());

		try {

			reservationRepository.save(reservation);

		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		this.emailService.processSendEmail(createReservationRest.getEmail(), "RESERVATION",
				createReservationRest.getName());

		return locator;
	}

	// DELETE OR CANCEL RESERVATION
	@Override
	public String deleteReservation(String locator) throws BookingException {
		Reservation reservation = reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFoundException("LOCATOR_NOT_FOUND", "LOCATOR_NOT_FOUND"));

		try {
			reservationRepository.deleteByLocator(locator);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}

		this.emailService.processSendEmail(reservation.getEmail(), "CANCEL", reservation.getName());

		return "RESERVATION_CANCELED";
	}

	/****** PRIVATE METHODS ******/

	private Reservation getReservationEntity(Long reservationId) throws BookingException {
		return reservationRepository.findById(reservationId)
				.orElseThrow(() -> new NotFoundException("RESERVATION_NOT_FOUND", "RESERVATION_NOT_FOUND"));
	}

	private Reservation getReservationEntityByLocator(String locator) throws BookingException {
		return reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFoundException("RESERVATION_NOT_FOUND", "RESERVATION_NOT_FOUND"));
	}

	private String generateLocator(Restaurant restaurantId, CreateReservationRest createReservationRest)
			throws BookingException {

		return restaurantId.getName() + createReservationRest.getTurnId();
	}

}
