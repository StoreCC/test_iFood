package com.iFood.servicios.rest;


import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Predicate;

import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iFood.dto.City;
import com.iFood.servicios.util.ServicioGetMusic;
import com.iFood.servicios.util.ServicioWeather;

@RestController
@RequestMapping(value="/getMusicCity")
public class ServicioRest {

	private final ServicioWeather IservicioWeather;
	private final ServicioGetMusic IservicioGetMusic;
	
		
	public ServicioRest(ServicioWeather pServicioWeather, ServicioGetMusic pServicioGetMusic) {
		IservicioWeather = pServicioWeather;
		IservicioGetMusic = pServicioGetMusic;
	}
	
	
	
	@GetMapping	
	public String getTracksList(@RequestParam("name") String name) throws UnknownHostException, JSONException  {
		System.out.println(name);
		City city= getInfoCity(name);
		String genero="classical";
//		If temperature (celcius) is above 30 degrees, suggest tracks for party
//		In case temperature is between 15 and 30 degrees, suggest pop music tracks
//		If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
//		Otherwise, if it's freezing outside, suggests classical music tracks
				 
		if(city.getTemp()>30) {
			genero="party";
		}
		if(city.getTemp()>=15 &&city.getTemp()<=30) {
			genero="pop";
		}
		if(city.getTemp()>=10 &&city.getTemp()<=14) {
			genero="rock";
		}
		String trackList = getTrackList(genero,city.getCountry());
		
		
		return "Temperatura: "+trackList;
		
	}
	private String getTrackList(String genero, String country) throws JSONException, UnknownHostException {
		return IservicioGetMusic.getMusicCity(genero, country);
		}




	private City getInfoCity(String name)  {
		try {
			return IservicioWeather.getWeatherCity(name);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
}
