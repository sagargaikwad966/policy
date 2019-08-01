package com.policy.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.policy.entity.Policy;
import com.policy.entity.User;
import com.policy.entity.UserPolicy;
import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;
import com.policy.repository.UserPolicyRepository;
import com.policy.repository.UserRepository;
import com.policy.service.PolicyService;
import com.policy.serviceimpl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest 
{
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserServiceImpl userServiceImplMock;
	
	@Mock
	PolicyService policyServiceMock;
	
	@Mock
	UserPolicyRepository userPolicyRepositoryMock;

	@Mock
	UserRepository userRepositoryMock;

	User user = new User();
	
	Policy policy = new Policy();
	
	UserPolicy userPolicy = new UserPolicy();
	
	UserPolicyModel userPolicyModel = new UserPolicyModel();
	
	Optional<User> optionalUser = Optional.of(user);
	
	@Before
	public void setUp()
	{
		user.setUserId("USER_PRI_2");
		user.setDob(LocalDate.of(1995, 03, 01));
		
		policy.setPolicyId("POL_CRP_3");
		policy.setAgeLimit("18-70");
		
		userPolicy.setUserPolicyId("USER_PRI_2-POL_CRP_3-1");
		userPolicy.setPolicyId(policy.getPolicyId());
		userPolicy.setUserId(user.getUserId());
		userPolicy.setStatus("ACTIVE");
		
		userPolicyModel.setUserPolicyId("USER_PRI_2-POL_CRP_3-1");
		
		optionalUser = Optional.of(user);
	}
	
	
	
	
	@Test
	public void testPurchasePolicySuccess() throws PolicyException
	{
		Mockito.when(userRepositoryMock.findById(Mockito.anyString())).thenReturn(optionalUser);
		Mockito.when(policyServiceMock.getPolicy(Mockito.anyString())).thenReturn(policy);
		Mockito.when(userPolicyRepositoryMock.save(Mockito.any(UserPolicy.class))).thenReturn(userPolicy);
		UserPolicyModel purchasePolicy = userServiceImpl.purchasePolicy("USER_PRI_2", "POL_CRP_3", true);
		
		assertNotNull(purchasePolicy);
		assertEquals("USER_PRI_2-POL_CRP_3-1", purchasePolicy.getUserPolicyId());
	}
	
	@Test(expected = PolicyException.class)
	public void testPurchasePolicyFailure() throws PolicyException
	{
		userServiceImpl.purchasePolicy("USER_PRI_2", "POL_CRP_3", false);
		
	}
	
	@Test(expected = PolicyException.class)
	public void testPurchasePolicyFailureInvalidUser() throws PolicyException
	{
		Mockito.when(userRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.empty());
		userServiceImpl.purchasePolicy("USER_PRI_2", "POL_CRP_3", true);
		
	}
	
	@Test(expected = PolicyException.class)
	public void testPurchasePolicyFailureByAge() throws PolicyException
	{
		
		user.setDob(LocalDate.of(2002, 03, 01));
		optionalUser = Optional.of(user);
		Mockito.when(userRepositoryMock.findById(Mockito.anyString())).thenReturn(optionalUser);
		Mockito.when(policyServiceMock.getPolicy(Mockito.anyString())).thenReturn(policy);
		UserPolicyModel purchasePolicy = userServiceImpl.purchasePolicy("USER_PRI_2", "POL_CRP_3", true);
		
		assertNotNull(purchasePolicy);
		assertEquals("USER_PRI_2-POL_CRP_3-1", purchasePolicy.getUserPolicyId());
	}

}
