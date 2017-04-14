package com.sample.app.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonInput {
	
	@JsonProperty("data")
	private Data data;

	

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
}
