package com.sample.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test() {
		return "index";
	}
}
