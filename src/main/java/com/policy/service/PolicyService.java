package com.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.policy.exception.PolicyException;
import com.policy.model.PolicyListModel;
import com.policy.model.PolicyModel;

@Service
public interface PolicyService {

	public List<PolicyListModel> availablePolicies() throws PolicyException;

	public PolicyModel policyDetails(String policyId) throws PolicyException;

}
