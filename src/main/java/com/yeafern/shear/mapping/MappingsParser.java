package com.yeafern.shear.mapping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MappingsParser {

	private static final String COMMENT_START = "#";
	private static final String FIELD_METHOD_START = "    ";
	
	private MappingsParser() { }
	
	public static Mappings parse(String file) {
		try {
			Map<String, String> classMap = new HashMap<String, String>();
						
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.startsWith(COMMENT_START)) {
					continue;
				}
				
				if(line.startsWith(FIELD_METHOD_START)) {
					// TODO
				} else {
					String[] split = line.split(" -> ");
					if(split.length == 2) {
						String original = split[0].trim();
						String obfuscated = split[1].trim();
						if(obfuscated.endsWith(":")) {
							obfuscated = obfuscated.substring(0, obfuscated.length() - 1);
							
							classMap.put(renameClass(obfuscated), renameClass(original));
						}
					}
				}
			}
			
			reader.close();
			return new Mappings(classMap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String renameClass(String clazz) {
		return clazz.replace('.', '/');
	}
}
