package com.sample.app.main;

import com.sample.app.server.SampleAppApplication;
import com.sample.app.service.first.FirstService;
import com.sample.app.service.second.SecondService;

public class TestMain {
	public static void main(String[] args) {

		String serverName = "NO-VALUE";

		switch (args.length) {
		case 2:
			// Optionally set the HTTP port to listen on, overrides
			// value in the <server-name>-server.yml file
			System.setProperty("server.port", args[1]);
			// Fall through into ..

		case 1:
			serverName = args[0].toLowerCase();
			break;

		default:
			usage();
			return;
		}

		if (serverName.equals("app")) {
			SampleAppApplication.main(args);
		}
		else if(serverName.equals("first")) {
			FirstService.main(args);
		}
		else if(serverName.equals("second")) {
			SecondService.main(args);
		}
		else{
			usage();
		}
	}

	protected static void usage() {
		System.out.println("Welcome to Microservices Testing : please enter args as app, first or second");
	}
}
