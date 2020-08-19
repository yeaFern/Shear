package com.yeafern.shear;

import java.io.IOException;

import com.yeafern.shear.api.VersionInfo;
import com.yeafern.shear.api.VersionManifest;
import com.yeafern.shear.api.VersionInfo.Download;
import com.yeafern.shear.api.VersionManifest.Version;
import com.yeafern.shear.util.FileDownloader;
import com.yeafern.shear.util.JSONFetcher;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.print("Fetching version manifest: ");
		VersionManifest manifest = JSONFetcher.fetch(VersionManifest.URL, VersionManifest.class);
		System.out.println("Done");

		Version version = manifest.getVersions().get(0);
		System.out.print("Fetching info for " + version.getID() + ": ");
		VersionInfo info = JSONFetcher.fetch(version.getUrl(), VersionInfo.class);
		System.out.println("Done");

		Download server = info.getDownloads().getServer();
		Download mappings = info.getDownloads().getServerMappings();

		String serverFileName = "server-" + version.getID() + ".jar";

		System.out.print("Downloading server: ");
		FileDownloader.download(server.getUrl(), serverFileName);
		System.out.println("Done");

		String mappingsFileName = "server-" + version.getID() + "-mappings.txt";

		System.out.print("Downloading server mappings: ");
		FileDownloader.download(mappings.getUrl(), mappingsFileName);
		System.out.println("Done");

		String outputFilename = "server-" + version.getID() + "-deob.jar";

		Shear shear = new Shear(mappingsFileName, serverFileName, outputFilename);
		shear.run();
	}
}
