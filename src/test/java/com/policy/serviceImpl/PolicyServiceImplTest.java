package com.policy.serviceImpl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.policy.entity.Policy;
import com.policy.exception.PolicyException;
import com.policy.model.PolicyListModel;
import com.policy.model.PolicyModel;
import com.policy.repository.PolicyRepository;
import com.policy.serviceimpl.PolicyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PolicyServiceImplTest {

	@InjectMocks
	PolicyServiceImpl policyServiceImpl;

	@Mock
	PolicyRepository policyRepository;

	List<Policy> policyList;
	List<Policy> policyListEmpty;
	PolicyModel policyModel;
	Optional<Policy> policyOptional;

	Policy policy;

	@Before
	public void setup() {
		policy = new Policy();
		policy.setAgeLimit("25-65");
		policy.setMaturityPeriod(6);
		policy.setPolicyId("POC12");
		policy.setPolicyName("Life insurance");
		policy.setPremium(4567.8);
		policy.setSumInsured(23456.00);
		policy.setTotalPremium(4567.00);
		policy.setType("asdf");
		policy.setStatus("ACTIVE");

		policyList = new ArrayList<>();
		policyList.add(policy);

		policyListEmpty = new ArrayList<>();

		policyModel = new PolicyModel();
		policyModel.setAgeLimit("25-65");
		policyModel.setMaturityPeriod(6);
		policyModel.setPolicyId("POC12");
		policyModel.setPolicyName("Life insurance");
		policyModel.setPremium(4567.8);
		policyModel.setSumInsured(23456.00);
		policyModel.setTotalPremium(4567.00);
		policyModel.setType("asdf");
		
		policyOptional = Optional.of(policy);
	}

	@Test
	public void testAvailablePolicies() throws PolicyException {
		Mockito.when(policyRepository.findAllByStatus("ACTIVE")).thenReturn(policyList);
		List<PolicyListModel> response = policyServiceImpl.availablePolicies();
		assertNotNull(response);
	}

	@Test(expected = PolicyException.class)
	public void testAvailablePoliciesFailure() throws PolicyException {
		Mockito.when(policyRepository.findAllByStatus("ACTIVE")).thenReturn(policyListEmpty);
		List<PolicyListModel> response = policyServiceImpl.availablePolicies();
	}

	@Test
	public void testPolicyDetails() throws PolicyException {
		Mockito.when(policyRepository.findByPolicyIdAndStatus("POC12", "ACTIVE")).thenReturn(policyOptional);
		PolicyModel response = policyServiceImpl.policyDetails("POC12");
		assertNotNull(response);
	}

	@Test(expected = PolicyException.class)
	public void testPolicyDetailsFailure() throws PolicyException {
		Mockito.when(policyRepository.findByPolicyIdAndStatus("POC12", "ACTIVE")).thenReturn(Optional.empty());
		PolicyModel response = policyServiceImpl.policyDetails("POC12");
	}
	
	@Test
	public void testGetPolicy() throws PolicyException {
		Mockito.when(policyRepository.findById("POC12")).thenReturn(policyOptional);
		Policy response=policyServiceImpl.getPolicy("POC12");
		assertNotNull(response);
	}
	
	@Test(expected = PolicyException.class)
	public void testGetPolicyFailure() throws PolicyException {
		Mockito.when(policyRepository.findById("POC12")).thenReturn(Optional.empty());
		Policy response=policyServiceImpl.getPolicy("POC12");
		assertNotNull(response);
	}
}
