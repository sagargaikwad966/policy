package com.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;

@Service
public interface UserPolicyService {

	List<UserPolicyModel> getUserPolicyByUserId(String userId) throws PolicyException;

}
