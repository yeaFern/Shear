package com.yeafern.shear.mapping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MappingsParser {

	private static final String FIELD_METHOD_START = "    ";

	// Matches a qualified java class.
	// e.g. net.minecraft.advancements.critereon.EnchantedItemTrigger$TriggerInstance
	//      net.minecraft.advancements.critereon.package-info
	public static final String CLASS_REGEX = "(([a-zA-Z_$][-a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][-a-zA-Z\\d_$]*)";

	// Matches a qualified java type.
	// e.g. net.minecraft.advancements.critereon.EnchantedItemTrigger$TriggerInstance[][]
	public static final String TYPE_REGEX  = "(([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*)(\\[\\])*";

	// Matches a method name.
	// e.g. <init>, myFunction
	public static final String METHOD_NAME_REGEX  = "(<?[a-zA-Z_$][a-zA-Z\\d_$]*>?)";

	// Matches a field name.
	// e.g. integer4, $value, _123
	public static final String FIELD_NAME_REGEX  = "([a-zA-Z_$][a-zA-Z\\d_$]*)";

	public static final Pattern CLASS_LINE_PATTERN = Pattern.compile(CLASS_REGEX + " -> " + CLASS_REGEX + ":");
	public static final Pattern FIELD_LINE_PATTERN = Pattern.compile(FIELD_METHOD_START + TYPE_REGEX + " " + FIELD_NAME_REGEX + " -> " + FIELD_NAME_REGEX);
	public static final Pattern METHOD_LINE_PATTERN = Pattern.compile(FIELD_METHOD_START + "(\\d+:\\d+:)?" + TYPE_REGEX + " " + METHOD_NAME_REGEX  + "\\(.*\\) -> " + METHOD_NAME_REGEX);
	public static final Pattern COMMENT_LINE_PATTERN = Pattern.compile("#.*");

	private MappingsParser() { }

	// Note that this is not a /complete/ implementation of the ProGuard format, however
	// it seems to be good enough for the Minecraft mappings for now.
	// See https://www.guardsquare.com/en/products/proguard/manual/retrace for the spec.
	// Also my regex skills are lacking so I'm not sure if this handles /every/ case,
	// should probably devise some test cases at some point.
	public static Mappings parse(String file) {
		try {
			Map<String, String> classMap = new HashMap<String, String>();

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int currentLine = 1;
			while((line = reader.readLine()) != null) {
				Matcher commentLineMatcher = COMMENT_LINE_PATTERN.matcher(line);
				Matcher classLineMatcher = CLASS_LINE_PATTERN.matcher(line);
				Matcher fieldLineMatcher = FIELD_LINE_PATTERN.matcher(line);
				Matcher methodLineMatcher = METHOD_LINE_PATTERN.matcher(line);

				if(commentLineMatcher.matches()) {
					continue;
				} else if(classLineMatcher.matches()) {
					String original = classLineMatcher.group(1);
					String obfuscated = classLineMatcher.group(3);

					classMap.put(renameClass(obfuscated), renameClass(original));
				} else if(fieldLineMatcher.matches()) {
					String original = fieldLineMatcher.group(4);
					String obfuscated = fieldLineMatcher.group(5);
				} else if(methodLineMatcher.matches()) {
					String original = methodLineMatcher.group(5);
					String obfuscated = methodLineMatcher.group(6);
				} else {
					reader.close();
					throw new RuntimeException("Failed to parse mapping file at line " + currentLine);
				}

				currentLine++;
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
