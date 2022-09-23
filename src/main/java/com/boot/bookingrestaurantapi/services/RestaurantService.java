package com.boot.bookingrestaurantapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateRestaurantRest;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;

@Service
public interface RestaurantService {
	
	RestaurantRest getRestaurantById(Long restaurantId) throws BookingException;
	
	RestaurantRest getRestaurantByName(String name) throws BookingException;
	
	RestaurantRest createRestaurant(CreateRestaurantRest createRestaurantRest) throws BookingException;
	
	List<RestaurantRest> getRestaurants() throws BookingException;
}
