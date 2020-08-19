package com.yeafern.shear;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.tree.ClassNode;

import com.yeafern.shear.mapping.MappingsParser;
import com.yeafern.shear.remap.ShearRemapper;
import com.yeafern.shear.mapping.Mappings;

public class Shear {

	private static int totalFiles = 0;
	private static int currentFile = 0;
	
	private Mappings mappings = null;
	
	private File inputFile;
	private File outputFile;
	
	public Shear(String mappingFile, String inputFile, String outputFile) {
		this.mappings = MappingsParser.parse(mappingFile);
		this.inputFile = new File(inputFile);
		this.outputFile = new File(outputFile);
	}
	
	public void run() throws IOException {
		if(outputFile.exists()) {
			outputFile.delete();
		}
		
		ZipFile input = new ZipFile(inputFile);
		Shear.totalFiles = input.size();
		Shear.currentFile = 0;
		
		ZipOutputStream output = new ZipOutputStream(new FileOutputStream(outputFile));
		
		System.out.println("Processing " + Shear.totalFiles + " files...");
		
		input.stream().forEach(entry -> {
			Shear.currentFile++;
			try {
				if(entry.getName().endsWith(".class")) {
					ClassReader reader = new ClassReader(input.getInputStream(entry));
					handleClassFile(reader, output);
				} else {
					handleOtherFile(entry, output, input);
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			//System.out.print("\r" + Shear.currentFile + "/" + Shear.totalFiles);
		});
		
		System.out.println("\nDone.");

		input.close();
		output.close();
	}
	
	private void handleClassFile(ClassReader reader, ZipOutputStream output) throws IOException {
		ClassNode node = new ClassNode();
		reader.accept(new ClassRemapper(node, new ShearRemapper(mappings)), 0);

		ClassWriter writer = new ClassWriter(0);
		node.accept(writer);
		
		output.putNextEntry(new ZipEntry(node.name + ".class"));
		output.write(writer.toByteArray());
		output.closeEntry();
	}
	
	private void handleOtherFile(ZipEntry entry, ZipOutputStream output, ZipFile zipfile) throws IOException {
        ZipEntry newEntry = new ZipEntry(entry.getName()); 
        output.putNextEntry(newEntry);
        
        InputStream in = zipfile.getInputStream(entry);
        
        byte[] buffer = new byte[512];
        while (0 < in.available()){
            int read = in.read(buffer);
            if (read > 0) {
            	output.write(buffer,0,read);
            }
        }
	}
}
