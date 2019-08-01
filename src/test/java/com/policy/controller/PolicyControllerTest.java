package com.policy.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.policy.exception.PolicyException;
import com.policy.model.PolicyAnalysis;
import com.policy.model.PolicyListModel;
import com.policy.model.PolicyModel;
import com.policy.model.ResponseData;
import com.policy.model.TrendModel;
import com.policy.service.PolicyService;

@RunWith(MockitoJUnitRunner.class)
public class PolicyControllerTest {

	@Mock
	PolicyService policyService;

	@InjectMocks
	PolicyController policyController;

	PolicyListModel policyListModel;
	List<PolicyListModel> policyList;
	PolicyModel policyModel;

	@Before
	public void setup() {
		policyListModel = new PolicyListModel();
		policyListModel.setPolicyId("POCID12");
		policyListModel.setPolicyName("Life Insurance");
		
		policyList=new ArrayList<>();
		policyList.add(policyListModel);

		policyModel = new PolicyModel();
		policyModel.setAgeLimit("25-65");
		policyModel.setMaturityPeriod(6);
		policyModel.setPolicyId("POC12");
		policyModel.setPolicyName("Life insurance");
		policyModel.setPremium(4567.8);
		policyModel.setSumInsured(23456.00);
		policyModel.setTotalPremium(4567.00);
		policyModel.setType("asdf");
	}

	@Test
	public void testAvailablePolicies() throws PolicyException {

		Mockito.when(policyService.availablePolicies()).thenReturn(policyList);
		ResponseEntity<List<PolicyListModel>> response =policyController.availablePolicies();
		assertNotNull(response);
	}

	@Test
	public void testPolicyDetails() throws PolicyException {

		Mockito.when(policyService.policyDetails("POC12")).thenReturn(policyModel);
		ResponseEntity<PolicyModel> policy = policyController.policyDetails("POC12");
		assertNotNull(policy);
		
	}
	@Test
	public void testGetPolicyAnalysis() {

		TrendModel trendModel = new TrendModel();
		Map<String, PolicyAnalysis> policyAnalysisMap = new HashMap();

		PolicyAnalysis policyAnalysis = new PolicyAnalysis();

		policyAnalysis.setName("Life Insurance");
		policyAnalysis.setNumberOfPoliciesSold(5D);
		policyAnalysis.setPercentage("100 %");
		policyAnalysisMap.put("POL_LIF_1", policyAnalysis);	

		trendModel.setTotalNoOfPoliciesSold(10D);
		trendModel.setPolicyAnalysisMap(policyAnalysisMap);

		Mockito.when(policyService.getPolicyAnalysis("monthly")).thenReturn(trendModel);
		
		ResponseEntity<ResponseData> response = policyController.getPolicyAnalysis("monthly");
		
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(response.getBody().getData(), trendModel);
	}
}
