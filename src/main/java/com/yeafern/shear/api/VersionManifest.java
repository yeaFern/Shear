package com.yeafern.shear.api;

import java.util.List;

public class VersionManifest {

	public static final String URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
	
	private Latest latest;
	private List<Version> versions;
	
	public Latest getLatest() {
		return latest;
	}
	
	public List<Version> getVersions() {
		return versions;
	}

	public class Latest {
		
		private String release;
		private String snapshot;
		
		public String getRelease() {
			return release;
		}
		
		public String getSnapshot() {
			return snapshot;
		}
	}
	
	public class Version {
		
		private String id;
		private String type;
		private String url;
		private String time;
		private String releaseTime;
	
		public String getID() {
			return id;
		}
		
		public String getType() {
			return type;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getTime() {
			return time;
		}
		
		public String getReleaseTime() {
			return releaseTime;
		}
	}
	
}
