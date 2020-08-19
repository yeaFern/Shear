package com.yeafern.shear;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.yeafern.shear.api.VersionInfo;
import com.yeafern.shear.api.VersionManifest;
import com.yeafern.shear.api.VersionInfo.Download;
import com.yeafern.shear.api.VersionManifest.Version;

public class Main {

	public static void main(String[] args) throws IOException {

		VersionManifest manifest = null;
		{
			System.out.print("Fetching version manifest: ");
			URL url = new URL(VersionManifest.URL);
			InputStreamReader input = new InputStreamReader(url.openStream());
			JsonReader reader = new JsonReader(input);
			Gson gson = new Gson();
			manifest = gson.fromJson(reader, VersionManifest.class);
			System.out.println("Done");
		}

		VersionInfo info = null;
		Version version = manifest.getVersions().get(0);
		{
			System.out.print("Fetching info for " + version.getID() + ": ");
			URL url = new URL(version.getUrl());
			InputStreamReader input = new InputStreamReader(url.openStream());
			JsonReader reader = new JsonReader(input);
			Gson gson = new Gson();
			info = gson.fromJson(reader, VersionInfo.class);
			System.out.println("Done");
		}

		Download server = info.getDownloads().getServer();
		Download mappings = info.getDownloads().getServerMappings();

		{
			System.out.print("Downloading server: ");
			BufferedInputStream in = new BufferedInputStream(new URL(server.getUrl()).openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("server.jar");

			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}

			fileOutputStream.close();
			System.out.println("Done");
		}

		{
			System.out.print("Downloading server mappings: ");
			BufferedInputStream in = new BufferedInputStream(new URL(mappings.getUrl()).openStream());
			FileOutputStream fileOutputStream = new FileOutputStream("server.txt");

			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}

			fileOutputStream.close();
			System.out.println("Done");
		}

		Shear shear = new Shear("server.txt", "server.jar", "server-deob.jar");
		shear.run();
	}
}
