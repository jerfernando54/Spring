package com.boot.bookingrestaurantapi.jsons;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReservationRest {
	
	@JsonProperty("date")
    private Date date;
	
	@JsonProperty("restaurantId")
    private Long restaurantId;
	
	@JsonProperty("person")
    private Long person;
	
	@JsonProperty("turnId")
    private Long turnId;
	
	@JsonProperty("name")
    private String name;
	
	@JsonProperty("email")
    private String email;
	
	@JsonProperty("payment")
    private Boolean payment;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getPerson() {
		return person;
	}

	public void setPerson(Long person) {
		this.person = person;
	}

	public Long getTurnId() {
		return turnId;
	}

	public void setTurnId(Long turnId) {
		this.turnId = turnId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
