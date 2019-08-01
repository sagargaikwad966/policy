package com.policy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.exception.PolicyException;
import com.policy.model.PolicyModel;
import com.policy.model.ResponseData;
import com.policy.service.PolicyService;

@RestController
@RequestMapping("/policies")
public class PolicyController {

	@Autowired
	PolicyService policyService;

	@GetMapping
	public ResponseEntity<ResponseData> availablePolicies() throws PolicyException {

		List<PolicyModel> policyModelList = policyService.availablePolicies();
		Map<Integer, String> status = new HashMap();
		status.put(200, "SUCCESSFULL TRANSACTION");
		ResponseData response = new ResponseData("Please find below list of available policies ", status,
				policyModelList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/{policy_id}")
	public ResponseEntity<ResponseData> policyDetails(@PathVariable("policy_id") String policyId)
			throws PolicyException {

		PolicyModel policyModel = policyService.policyDetails(policyId);
		Map<Integer, String> status = new HashMap();
		status.put(200, "SUCCESSFULL TRANSACTION");
		ResponseData response = new ResponseData("Please find below details of policy Id : " + policyId, status,
				policyModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
