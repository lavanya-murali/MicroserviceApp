package com.sample.app.service.second;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.rest.Data;
import com.sample.app.rest.JsonInput;
import com.sample.app.sequence.SequenceSelector;
import com.sample.app.service.first.ConfigurationService;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@ComponentScan("com.sample.app,com.sample.app.sequence")
public class SecondService {
	
	@Autowired
	ConfigurationService configurationService;

	protected Logger logger = Logger.getLogger(SecondService.class
			.getName());
	@Autowired
	SequenceSelector sequenceSelector;

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		
	// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "second-service");
		SpringApplication.run(SecondService.class, args);
	}
	
	@RequestMapping("/second")
    public String greeting() {
        return "Hello from Second!";
    }
	
	@RequestMapping(value = "/swapsecond/{swaptype}/{swapfunction}", method = RequestMethod.POST,consumes={"application/json"})
	 public ResponseEntity<?> swapInSecond(@RequestBody JsonInput inp,@PathVariable String swaptype, @PathVariable String swapfunction) {
		logger.info("swapInSecond");
		logger.info("swaptype"+  swaptype);
		logger.info("swapfunction"+  swapfunction);
		logger.info("inp"+  inp);
		Data data = sequenceSelector.swap(swaptype, swapfunction, inp.getData());
		JsonInput jsonInput = new JsonInput();
		jsonInput.setData(data);
		return new ResponseEntity<JsonInput>(jsonInput,HttpStatus.OK);
	 }

}
