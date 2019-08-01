package com.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.policy.model.UserPolicyModel;

@Service
public interface UserPolicyService {

	List<UserPolicyModel> getUserPolicyByUserId(String UserId) throws Exception;

}