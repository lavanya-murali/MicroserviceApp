package com.sample.app.service.second;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.app.rest.JsonInput;

@FeignClient("second-service")
public interface SecondClient {
    @RequestMapping("/greeting")
    String greeting();
    
    @RequestMapping(value = "/swapsecond/{swaptype}/{swapfunction}", method = RequestMethod.POST,consumes={"application/json"})
	 public ResponseEntity<?> swapInSecond(@RequestBody JsonInput  inp,@PathVariable String swaptype, @PathVariable String swapfunction);

}
