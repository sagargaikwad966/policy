package com.policy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.policy.exception.PolicyException;
import com.policy.model.ResponseData;
import com.policy.model.UserPolicyModel;
import com.policy.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	UserService userService;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/policy/purchase/{userId}")
	public ResponseEntity<ResponseData> purchasePolicy(@PathVariable("userId") String userId, @RequestParam("policyId") String policyId, @RequestParam("t&c") Boolean TnC) throws PolicyException
	{
		UserPolicyModel userPolicyModel = userService.purchasePolicy(userId, policyId, TnC);
		Map<Integer, String> status = new HashMap();
		status.put(200, "SUCCESSFULL TRANSACTION");
		ResponseData response = new ResponseData("Policy Purchased successfully", status, userPolicyModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	

}
