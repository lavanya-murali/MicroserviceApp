package com.sample.app.service.first;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.app.rest.JsonInput;

@FeignClient("first-service")
public interface FirstClient {
	
    @RequestMapping("/greeting")
    String greeting();
    
    @RequestMapping(value = "/swap", method = RequestMethod.POST,produces={"application/json"},consumes={"application/json"} )
    ResponseEntity<?> swapFirst(@RequestBody JsonInput input);
}
