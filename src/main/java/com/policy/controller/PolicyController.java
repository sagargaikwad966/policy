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
import com.policy.model.PolicyListModel;
import com.policy.model.PolicyModel;
import com.policy.model.ResponseData;
import com.policy.model.TrendModel;
import com.policy.service.PolicyService;

@RestController
@RequestMapping("/policies")
public class PolicyController {

	@Autowired
	PolicyService policyService;

	@GetMapping
	public ResponseEntity<List<PolicyListModel>> availablePolicies() throws PolicyException {

		List<PolicyListModel> policyModelList = policyService.availablePolicies();
		return new ResponseEntity<>(policyModelList, HttpStatus.OK);

	}

	@GetMapping("/{policy_id}")
	public ResponseEntity<PolicyModel> policyDetails(@PathVariable("policy_id") String policyId)
			throws PolicyException {

		PolicyModel policyModel = policyService.policyDetails(policyId);
		return new ResponseEntity<>(policyModel, HttpStatus.OK);
	}
	@GetMapping("/trend/{sortBy}")
	public ResponseEntity<ResponseData> getPolicyAnalysis(@PathVariable(value = "sortBy") String sortBy){

		Map<Integer, String> statusMap = new HashMap();
		statusMap.put(200, "Search successful.");
		TrendModel trendModel = policyService.getPolicyAnalysis(sortBy);
		ResponseData response = new ResponseData("Policy trend Analysis is as below: ", statusMap, trendModel);

		return new ResponseEntity(response, HttpStatus.OK);
	}
}
