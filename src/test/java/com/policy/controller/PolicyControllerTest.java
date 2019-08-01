package com.policy.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.policy.model.PolicyListModel;
import com.policy.service.PolicyService;

@RunWith(MockitoJUnitRunner.class)
public class PolicyControllerTest {
	
	@Mock
	PolicyService policyService;
	
	@InjectMocks
	PolicyController policyController;
	
	PolicyListModel policyListModel;
	
	public void setup() {
		policyListModel =new PolicyListModel();
		policyListModel.setPolicyId("POCID12");
		policyListModel.setPolicyName("Life Insurance");
	}
	
	@Test
	public void availablePolicies() {
		
		
	}

}
