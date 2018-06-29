package com.iFood.servicios.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ServicioGetMusic implements InterfaceServicioGetMusic {
	private final static String URLAutorize ="https://accounts.spotify.com/authorize/?";
	private final static String PARAMS="client_id=5fe01282e44241328a84e7c5cc169165&response_type=code&redirect_uri=";
	private final static String URI ="http://localhost:9991/getMusicCity";
	private final static String URLToken ="https://accounts.spotify.com/api/token";
	private final static String URLgenere ="https://api.spotify.com/v1/browse/categories/";
	
	@Override
	public String getMusicCity(String genero, String country) throws JSONException {
		// TODO Auto-generated method stub
		String trackList="";
		ResteasyClient client = new ResteasyClientBuilder().build();
		StringBuffer uri= new StringBuffer();
		uri.append(URLAutorize);
		uri.append(PARAMS);
		uri.append(URI);
		ResteasyWebTarget target = client.target(uri.toString());
		Response response = target.request().get();
		String userToken  = response.readEntity(String.class);
		
		ResteasyClient client2 = new ResteasyClientBuilder().build();
		ResteasyWebTarget target2 = client2.target(URLToken);
		Form form = new Form();
        form.param("code", userToken).param("redirect_uri", URI).param("grant_type", "authorization_code");
        Entity<Form> entity = Entity.form(form);
		Response response2;
		try {
			response2 = target2.request().header("Authorization","Basic "+Base64.getEncoder().encodeToString(
			        "f838704962dd4e88a7951b6697c7639a:581112eb38a34032b68ccb283f0c7698".getBytes("utf-8"))).post(Entity.entity(entity, MediaType.APPLICATION_JSON));
			String value = response.readEntity(String.class);
			response2.close();
			JSONArray json = new JSONArray("["+value+"]");
			JSONObject jsonObjectMain = (JSONObject) json.get(0);	
			String token=jsonObjectMain.getString("access_token");
			
			ResteasyClient clientTrackList = new ResteasyClientBuilder().build();
			ResteasyWebTarget targetTrackList = clientTrackList.target(URLgenere+genero+"/playlists?country="+country+"&limit=2");
			
	        Entity<Form> entityM = Entity.form(form);
			Response responseTrackList =targetTrackList.request().header("Authorization","Basic "+token).post(entityM);
			trackList = responseTrackList.readEntity(String.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return trackList;
	}
	

}
