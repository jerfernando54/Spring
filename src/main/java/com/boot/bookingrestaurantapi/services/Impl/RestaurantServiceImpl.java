package com.boot.bookingrestaurantapi.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.InternalServerErrorException;
import com.boot.bookingrestaurantapi.exceptions.NotFoundException;
import com.boot.bookingrestaurantapi.jsons.CreateRestaurantRest;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	@Autowired
	RestaurantRepository restaurantRepository;

	public static final ModelMapper modelmapper = new ModelMapper();

	public RestaurantRest getRestaurantById(Long restaurantId) throws BookingException {
		return modelmapper.map(getRestaurantEntity(restaurantId), RestaurantRest.class);

	}
	
	@Override
	public RestaurantRest getRestaurantByName(String name) throws BookingException {
		return modelmapper.map(getRestaurantEntityByName(name), RestaurantRest.class);
	}

	public List<RestaurantRest> getRestaurants() throws BookingException {
		final List<Restaurant> restaurantEntities = restaurantRepository.findAll();
		return restaurantEntities.stream().map(service -> modelmapper.map(service, RestaurantRest.class))
                .collect(Collectors.toList());
	}
	
	@Override
	public RestaurantRest createRestaurant(CreateRestaurantRest createRestaurantRest) throws BookingException {
		
		final Restaurant restaurant = new Restaurant();
		restaurant.setName(createRestaurantRest.getName());
		restaurant.setDescription(createRestaurantRest.getDescription());
		restaurant.setAdress(createRestaurantRest.getAddress());
		restaurant.setImage(createRestaurantRest.getImage());
		
		
		final RestaurantRest restaurantRest= new RestaurantRest();
		
		restaurantRest.setName(restaurant.getName());
		restaurantRest.setDescription(restaurant.getDescription());
		restaurantRest.setAddress(restaurant.getAddress());
		restaurantRest.setImage(restaurant.getImage());
		
		try {
			restaurantRepository.save(restaurant);
		} catch (Exception e) {
			LOGGER.error("INTERNAL_SERVER_ERROR", e);
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		return restaurantRest;
	}
	
	
	/*PRIVATE METHODS*/
	private Restaurant getRestaurantEntity(Long restaurantId) throws BookingException {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESTAURANT_NOT_FOUND"));
	}
	
	private Restaurant getRestaurantEntityByName(String restaurantName) throws BookingException {
		return restaurantRepository.findByName(restaurantName)
				.orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESTAURANT_NOT_FOUND"));
	}

	

	
}
