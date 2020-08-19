package com.yeafern.shear.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownloader {

	private FileDownloader() {}
	
	public static void download(String url, String file) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		byte dataBuffer[] = new byte[1024];
		int bytesRead;
		while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			fileOutputStream.write(dataBuffer, 0, bytesRead);
		}

		fileOutputStream.close();
	}
}
