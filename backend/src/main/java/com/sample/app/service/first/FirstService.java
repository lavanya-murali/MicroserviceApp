package com.sample.app.service.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
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
@EnableEurekaClient
@Controller
@ComponentScan(basePackages = "com.sample.app",
excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebService.class)  )
@PropertySource("classpath:swap.properties")
public class FirstService {
	
	@Value("${swap.type}")
	String swapType;
	
	@Value("${swap.function}")
	String swapFunction;
	
	@Autowired
	DefaultView defaultView;
	
	@Autowired
	WebService webService;
	/**
	 * URL uses the logical name of account-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String SECOND_SERVICE = "http://second-service";
	@Autowired
	public static void main(String[] args) {
        // Will configure using accounts-server.yml
        System.setProperty("spring.config.name", "first-service");

        SpringApplication.run(FirstService.class, args);
        
        
    }
	
	@RequestMapping(value = "/swap", method = RequestMethod.POST,produces={"application/json"},consumes={"application/json"})
	 public ResponseEntity<?> swapFirst(@RequestBody JsonInput input) {
		System.out.println("resttemplate"+restTemplate());
		JsonInput data = webService.swapArray(input,  swapType, swapFunction);
		return new ResponseEntity<JsonInput>(data, HttpStatus.OK);
	 }
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public WebService webService() {
		return new WebService(SECOND_SERVICE);
	}
	
	@Bean
	public DefaultView defaultView() {
		return new DefaultView();
	}

	
//	@Bean
//	public HomeController homeController() {
//		return new HomeController();
//	}
}
