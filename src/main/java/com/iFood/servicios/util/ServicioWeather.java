package com.iFood.servicios.util;

import java.net.UnknownHostException;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.iFood.dto.City;


@Service
public class ServicioWeather implements InterfaceServicioWeather {
	private final static String URL ="http://api.openweathermap.org/data/2.5/weather?APPID=47b7ec36e73e0234d1411ecfc65d4568&";


	private City getweather(String city)  {
		City iCity = new City();
		ResteasyClient client = new ResteasyClientBuilder().build();
		StringBuffer uri= new StringBuffer();
		uri.append(URL);
		uri.append(city);
		ResteasyWebTarget target = client.target(uri.toString());
		Response response = target.request().get();
		String value = response.readEntity(String.class);
		System.out.println("String"+value);
		
		JSONArray json;
		try {
			json = new JSONArray("["+value+"]");
			JSONObject jsonObjectMain = (JSONObject) json.get(0);	
			double temp=jsonObjectMain.getJSONObject("main").getDouble("temp");
			String country=jsonObjectMain.getJSONObject("sys").getString("country");
			iCity.setCountry(country);
			iCity.setTemp(temp);
			System.out.println("temp:"+iCity.getTemp()+" Country"+iCity.getCountry());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.close(); 
		return iCity;
	}
	public City getWeatherCity(String city) throws UnknownHostException {		
//		return getTemp("q="+city+"&units=metric"); 
		return getweather("q="+city+"&units=metric"); 
		
	}
	

}
