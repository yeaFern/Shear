package com.yeafern.shear;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Shear shear = new Shear("server.txt", "server.jar", "server-deob.jar");
		shear.run();
	}
}
