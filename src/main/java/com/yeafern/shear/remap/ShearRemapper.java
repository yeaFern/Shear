package com.yeafern.shear.remap;

import org.objectweb.asm.commons.Remapper;

import com.yeafern.shear.mapping.ClassMapping;
import com.yeafern.shear.mapping.Mappings;

public class ShearRemapper extends Remapper {
	
	private Mappings mappings;
	
	public ShearRemapper(Mappings mappings) {
		this.mappings = mappings;
	}
	
	@Override
	public String map(String internalName) {
		ClassMapping mapped = mappings.getClass(internalName);
		
		if(mapped == null) {
			return super.map(internalName);
		}

		return mapped.getName();
	}

	@Override
	public String mapMethodName(String owner, String name, String descriptor) {
		ClassMapping owningClass = mappings.getClass(owner);

		if(owningClass == null) {
			return super.mapMethodName(owner, name, descriptor);
		}

		String mapped = owningClass.mapMethod(name);

		if(mapped == null) {
			return super.mapMethodName(owner, name, descriptor);
		}

		return mapped;
	}

	@Override
	public String mapFieldName(String owner, String name, String descriptor) {
		ClassMapping owningClass = mappings.getClass(owner);

		if(owningClass == null) {
			return super.mapFieldName(owner, name, descriptor);
		}

		String mapped = owningClass.mapField(name);

		if(mapped == null) {
			return super.mapFieldName(owner, name, descriptor);
		}

		return mapped;
	}
}
