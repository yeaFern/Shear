package com.yeafern.shear.remap;

import org.objectweb.asm.commons.Remapper;

import com.yeafern.shear.mapping.Mappings;

public class ShearRemapper extends Remapper {
	
	private Mappings mappings;
	
	public ShearRemapper(Mappings mappings) {
		this.mappings = mappings;
	}
	
	@Override
	public String map(String internalName) {
		String mapped = mappings.mapClass(internalName);
		
		if(mapped == null) {				
			mapped = super.map(internalName);
		}

		return mapped;
	}
}
