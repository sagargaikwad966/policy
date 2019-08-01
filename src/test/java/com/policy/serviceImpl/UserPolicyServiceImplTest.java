package com.policy.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
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
import com.policy.entity.User;
import com.policy.entity.UserPolicy;
import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;
import com.policy.repository.UserPolicyRepository;
import com.policy.serviceimpl.UserPolicyServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserPolicyServiceImplTest
{
	@InjectMocks
	UserPolicyServiceImpl userPolicyServiceImpl;
	
	@Mock
	UserPolicyRepository userPolicyRepositoryMock;
	
	User user = new User();
	
	Policy policy = new Policy();
	
	UserPolicy userPolicy = new UserPolicy();
	
	UserPolicyModel userPolicyModel = new UserPolicyModel();
	
	List<UserPolicyModel> userPolicyModelList = new ArrayList<>();

	List<UserPolicy> userPolicyList = new ArrayList<>();
	
	Optional<List<UserPolicy>> userPolicyListOptional = Optional.of(userPolicyList);
	
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
		
		userPolicyList.add(userPolicy);
		userPolicyListOptional = Optional.of(userPolicyList);
	}
	
	@Test
	public void testGetUserPolicyByUserId() throws PolicyException
	{
		Mockito.when(userPolicyRepositoryMock.findByUserId(Mockito.anyString())).thenReturn(userPolicyListOptional);
		List<UserPolicyModel> userPolicyModelListResult = userPolicyServiceImpl.getUserPolicyByUserId("USER_PRI_2");
		
		assertNotNull(userPolicyModelListResult);
		assertEquals(1, userPolicyModelListResult.size());
	}
	
	@Test(expected = PolicyException.class)
	public void testGetUserPolicyByUserIdFailure() throws PolicyException
	{
		Mockito.when(userPolicyRepositoryMock.findByUserId(Mockito.anyString())).thenReturn(Optional.empty());
		userPolicyServiceImpl.getUserPolicyByUserId("USER_PRI_2");
		
	}

}
