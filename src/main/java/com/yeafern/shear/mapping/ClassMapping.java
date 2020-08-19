package com.yeafern.shear.mapping;

import java.util.HashMap;

public class ClassMapping {

	private String name;
	private HashMap<String, String> fields = new HashMap<String, String>();
	private HashMap<String, String> methods = new HashMap<String, String>();
	
	public ClassMapping(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String mapField(String name) {
		return fields.get(name);
	}
	
	public String mapMethod(String name) {
		return methods.get(name);
	}

	public void addField(String obfuscated, String original) {
		fields.put(obfuscated, original);
	}
	
	public void addMethod(String obfuscated, String original) {
		methods.put(obfuscated, original);
	}
}
