package com.yeafern.shear.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class JSONFetcher {

	private JSONFetcher() {}
	
	public static <T> T fetch(String jsonUrl, Type typeOfT) throws IOException {
		URL url = new URL(jsonUrl);
		InputStreamReader input = new InputStreamReader(url.openStream());
		JsonReader reader = new JsonReader(input);
		Gson gson = new Gson();
		return gson.fromJson(reader, typeOfT);
	}
}
