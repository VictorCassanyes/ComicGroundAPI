package com.proyecto.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ConvertirJson {
	
	public static void main(String[] args) {
		
		try {
			//Hacer la petición a la API de ComicVine con mi API Key y lo que quiero buscar
			String apiKey="39a3e2e44c5dc5386be927669d157c5ded28a94f";
			String name="fantastic%20%four";
			URL url = new URL("https://comicvine.gamespot.com/api/issues/?api_key="+apiKey+"&filter=name:"+name+"&field_list=name,image&format=json&limit=10");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//Propiedad de navegador para que permita hacer peticiones (ComicVine no permite hacerlo desde aplicación de origen desconocido)
			con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
					+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36");
			
			//Coger el input y pasarlo todo a una variable String
			InputStream is=con.getInputStream();
	        InputStreamReader isr=new InputStreamReader(is);
	        BufferedReader br=new BufferedReader(isr);
	        String linea="";
	        String comicsJson="";
	        while((linea=br.readLine())!=null) {
	        	comicsJson+=linea;
	        }
	        br.close();
	        isr.close();
	        is.close();
	        
			//Utilizado para dar buen formato al .json
	        Type type = new TypeToken<Map<String, Object>>() {}.getType(); 
	        Map<String, Object> data = new Gson().fromJson(comicsJson, type); 
	        String json = new GsonBuilder().setPrettyPrinting().create().toJson(data);
	        
	        //Se escribe en el archivo datos.json
	        FileWriter fw=new FileWriter("C:/Users/victo/Desktop/datos.json");
	        fw.write(json);
	        fw.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
