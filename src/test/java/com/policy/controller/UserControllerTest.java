package com.policy.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.itextpdf.text.DocumentException;
import com.policy.exception.PolicyException;
import com.policy.model.ResponseData;
import com.policy.model.UserPolicyModel;
import com.policy.serviceimpl.UserPolicyServiceImpl;
import com.policy.serviceimpl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest 
{
	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userServiceImplMock;
	
	@Mock
	UserPolicyServiceImpl userPolicyServiceImplMock;
	
	UserPolicyModel userPolicyModel = new UserPolicyModel();
	List<UserPolicyModel> userPolicyModelList = new ArrayList<>();
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
	
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
	
	@Test
	public void testUserPolicyFileReport() throws PolicyException, DocumentException
	{
		Mockito.when(userPolicyServiceImplMock.getUserPolicyByUserId(Mockito.anyString())).thenReturn(userPolicyModelList);
		Mockito.when(userServiceImplMock.pdfUserPolicyFileReport(userPolicyModelList)).thenReturn(byteArrayInputStream);
		
		ResponseEntity<InputStreamResource> userPolicyFileReport = userController.userPolicyFileReport("USER_PRI_2");
		
		assertNotNull(userPolicyFileReport);
	}
}
