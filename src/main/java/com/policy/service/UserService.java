package com.policy.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.policy.entity.User;
import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;

@Service
public interface UserService {

	UserPolicyModel purchasePolicy(String userId, String policyId, Boolean tnC) throws PolicyException;

	User getUser(String userId) throws PolicyException;

	ByteArrayInputStream pdfUserPolicyFileReport(List<UserPolicyModel> userPolicyModelList)
			throws DocumentException, PolicyException;
}
