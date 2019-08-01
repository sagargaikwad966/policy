package com.policy.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

	
	private String responseMessage;
	private Map<Integer, String> status;
	private Object data;

}
