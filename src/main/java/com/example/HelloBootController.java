package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloBootController {

	@Autowired
	HelloBootAppConfig appConfig;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello, " + appConfig.getMessage() + "!";
	}
}
