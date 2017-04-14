package com.sample.app.rest;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:swap.properties")
public class WebService{

	@Value("${swap.type}")
	String swapType;
	
	@Value("${swap.function}")
	String swapFunction;
	
	protected String serviceUrl;
	@LoadBalanced
	@Autowired
	@Qualifier("customRestTemplate")
	protected RestTemplate restTemplate;
	protected Logger logger = Logger.getLogger(WebService.class
			.getName());

	public WebService() {
		super();
	}
	
	public WebService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	
	
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}

	public JsonInput swapArray(JsonInput data) {
		
		logger.info("swapArray() invoked: for " +  ReflectionToStringBuilder.toString(data));
		logger.info("restTemplate():  " + restTemplate);
		logger.info("env.getProperty "+ swapType);
		logger.info("env.getProperty "+ swapFunction);
		HttpHeaders requestHeaders = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		requestHeaders.add(HttpHeaders.CONTENT_TYPE, javax.ws.rs.core.MediaType.APPLICATION_JSON);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(data, requestHeaders);
		ResponseEntity<JsonInput> entity = restTemplate.exchange(serviceUrl+ "/swapsecond/"+ swapType + "/" + swapFunction,HttpMethod.POST, httpEntity, JsonInput.class);
		return entity.getBody();
		
	}
	
	
	@Bean // this bean is required in order to pass properties into the @Value annotation, otherwise you will just get the literal string
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
