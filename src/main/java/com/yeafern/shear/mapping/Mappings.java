package com.yeafern.shear.mapping;

import java.util.Map;

public class Mappings {

	private final Map<String, ClassMapping> classes;

	public Mappings(final Map<String, ClassMapping> classes) {
		this.classes = classes;
	}

	public ClassMapping getClass(String name) {
		return classes.get(name);
	}
}
