package com.policy.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.policy.exception.PolicyException;
import com.policy.model.ResponseData;
import com.policy.model.UserPolicyModel;
import com.policy.serviceimpl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest 
{
	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userServiceImplMock;
	
	UserPolicyModel userPolicyModel = new UserPolicyModel();
	
	@Before
	public void setUp()
	{
		userPolicyModel.setUserPolicyId("USER_PRI_2-POL_CRP_3-1");
	}
	
	@Test
	public void testPurchasePolicy() throws PolicyException
	{
		Mockito.when(userServiceImplMock.purchasePolicy(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(userPolicyModel);
		ResponseEntity<ResponseData> purchasePolicyResponse = userController.purchasePolicy("USER_PRI_2", "POL_CRP_3", true);
		
		UserPolicyModel userModel = (UserPolicyModel) purchasePolicyResponse.getBody().getData();
		assertNotNull(purchasePolicyResponse);
		assertEquals("USER_PRI_2-POL_CRP_3-1", userModel.getUserPolicyId());
	}
}
