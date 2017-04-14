package com.sample.app.service.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.sample.app.rest.JsonInput;
import com.sample.app.rest.WebService;

@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@Controller
@ComponentScan("com.sample.app")
public class FirstService {
	
	@Autowired
	DefaultView defaultView;
	/**
	 * URL uses the logical name of account-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String SECOND_SERVICE = "http://localhost:3333";
	@Autowired
	public static void main(String[] args) {
        // Will configure using accounts-server.yml
        System.setProperty("spring.config.name", "first-service");

        SpringApplication.run(FirstService.class, args);
        
        
    }
	
	@RequestMapping(value = "/swap", method = RequestMethod.POST,produces={"application/json"},consumes={"application/json"})
	 public ResponseEntity<?> swapFirst(@RequestBody JsonInput input) {
		WebService webService = webservice();
		JsonInput data = webService.swapArray(input);
		return new ResponseEntity<JsonInput>(data, HttpStatus.OK);
	 }
	
	@LoadBalanced
	@Bean(name="customRestTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public WebService webservice() {
		return new WebService(SECOND_SERVICE);
	}

	
//	@Bean
//	public HomeController homeController() {
//		return new HomeController();
//	}
}
