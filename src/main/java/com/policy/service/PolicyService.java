package com.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.policy.exception.PolicyException;
import com.policy.model.PolicyModel;

@Service
public interface PolicyService {

	public List<PolicyModel> availablePolicies() throws PolicyException;

	public PolicyModel policyDetails(String policyId) throws PolicyException;

}
