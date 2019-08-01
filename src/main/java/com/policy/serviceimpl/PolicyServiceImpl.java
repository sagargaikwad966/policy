package com.policy.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.entity.Policy;
import com.policy.entity.User;
import com.policy.exception.PolicyException;
import com.policy.model.PolicyListModel;
import com.policy.model.PolicyModel;
import com.policy.repository.PolicyRepository;
import com.policy.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;

	@Override
	public List<PolicyListModel> availablePolicies() throws PolicyException {

		List<Policy> policyList = policyRepository.findAllByStatus("ACTIVE");
		if (policyList.isEmpty())
			throw new PolicyException("Sorry ...No policy is available");
		List<PolicyListModel> policyModelList = new ArrayList<>();
		for (Policy policy : policyList) {
			PolicyListModel policyModel = new PolicyListModel();
			BeanUtils.copyProperties(policy, policyModel);
			policyModelList.add(policyModel);
		}
		return policyModelList;
	}

	@Override
	public PolicyModel policyDetails(String policyId) throws PolicyException {

		Optional<Policy> policyOptional = policyRepository.findByPolicyIdAndStatus(policyId, "ACTIVE");
		if (policyOptional.isPresent()) {
			Policy policy = policyOptional.get();
			PolicyModel policyModel = new PolicyModel();
			BeanUtils.copyProperties(policy, policyModel);
			return policyModel;
		} else
			throw new PolicyException("Policy Id: " + policyId + " provided by you is not valid");
	}

	@Override
	public Policy getPolicy(String policyId) throws PolicyException {
		Optional<Policy> findByIdOptional = policyRepository.findById(policyId);
		
		Boolean isOptionalPresent = findByIdOptional.isPresent();
		
		if(isOptionalPresent)
			return findByIdOptional.get();
		else
			throw new PolicyException("User Not Found with Id : "+policyId);
	}
	
}