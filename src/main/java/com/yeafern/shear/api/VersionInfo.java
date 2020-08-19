package com.yeafern.shear.api;

import com.google.gson.annotations.SerializedName;

public class VersionInfo {

	private Downloads downloads;
	
	public Downloads getDownloads() {
		return downloads;
	}
	
	public class Downloads {
		
		private Download client;
		
		@SerializedName("client_mappings")
		private Download clientMappings;
		
		private Download server;
		
		@SerializedName("server_mappings")
		private Download serverMappings;
		
		public Download getClient() {
			return client;
		}
		
		public Download getClientMappings() {
			return clientMappings;
		}
		
		public Download getServer() {
			return server;
		}
		
		public Download getServerMappings() {
			return serverMappings;
		}
	}
	
	public class Download {
		
		private String sha1;
		private long size;
		private String url;
		
		public String getSha1() {
			return sha1;
		}
		
		public long getSize() {
			return size;
		}
		
		public String getUrl() {
			return url;
		}
	}
}
