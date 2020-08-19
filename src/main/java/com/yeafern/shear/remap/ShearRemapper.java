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

	@Override
	public String mapMethodName(String owner, String name, String descriptor) {
		return super.mapMethodName(owner, name, descriptor);
	}

	@Override
	public String mapFieldName(String owner, String name, String descriptor) {
		return super.mapFieldName(owner, name, descriptor);
	}
}
