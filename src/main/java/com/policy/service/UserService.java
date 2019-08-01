package com.policy.service;

import org.springframework.stereotype.Service;

import com.policy.entity.User;
import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;

@Service
public interface UserService {

	UserPolicyModel purchasePolicy(String userId, String policyId, Boolean tnC) throws PolicyException;

	User getUser(String userId) throws PolicyException;

}
