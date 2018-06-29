package com.iFood.servicios.util;

import java.net.UnknownHostException;

import com.iFood.dto.City;

public interface InterfaceServicioWeather {

	public City getWeatherCity(String city) throws UnknownHostException ;	
}
