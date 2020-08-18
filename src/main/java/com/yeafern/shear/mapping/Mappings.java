package com.yeafern.shear.mapping;

import java.util.Map;

public class Mappings {

	private final Map<String, String> classes;

	public Mappings(final Map<String, String> classes) {
		this.classes = classes;
	}

	public String mapClass(String name) {
		return classes.get(name);
	}
}
