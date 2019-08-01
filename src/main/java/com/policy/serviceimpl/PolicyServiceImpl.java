package com.policy.serviceimpl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.entity.Policy;
import com.policy.entity.UserPolicy;
import com.policy.exception.PolicyException;
import com.policy.model.PolicyAnalysis;
import com.policy.model.PolicyListModel;
import com.policy.model.PolicyModel;
import com.policy.model.TrendModel;
import com.policy.repository.PolicyRepository;
import com.policy.repository.UserPolicyRepository;
import com.policy.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	UserPolicyRepository userPolicyRepository;


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
	public TrendModel getPolicyAnalysis(String sortBy) {
		Map policyAnalysisMap = new HashMap();
		Policy policy = new Policy();
		TrendModel trendModel = new TrendModel();
		LocalDate date;
		DecimalFormat df = new DecimalFormat("0.00");


		if (sortBy.equalsIgnoreCase("Weekly")) {
			date = LocalDate.now().minusDays(7);
		} else if (sortBy.equalsIgnoreCase("Monthly")){
			date = LocalDate.now().minusMonths(1);
		} else {
			date = LocalDate.of(1800, 01, 01);
		}

		Double totalPolicies = userPolicyRepository.getTotalPoliciesCount(date, LocalDate.now());
		List<UserPolicy> policyList = userPolicyRepository.findAll();

		for(UserPolicy userPolicy : policyList) {

			Optional<Policy> policyOptional = policyRepository.findById(userPolicy.getPolicyId());

			if(policyOptional.isPresent()) {
				policy = policyOptional.get();
			}

			Double numberOfPoliciesSold = userPolicyRepository.getPolicyCount(userPolicy.getPolicyId(), date, LocalDate.now());
			Double percentage = Double.parseDouble(df.format(((numberOfPoliciesSold/totalPolicies) * 100.00)));

			PolicyAnalysis policyAnalysis = new PolicyAnalysis();

			policyAnalysis.setName(policy.getPolicyName());
			policyAnalysis.setNumberOfPoliciesSold(numberOfPoliciesSold);
			policyAnalysis.setPercentage(percentage);

			policyAnalysisMap.put(userPolicy.getPolicyId(), policyAnalysis);			
		}


		trendModel.setTotalNoOfPoliciesSold(totalPolicies);
		trendModel.setPolicyAnalysisMap(policyAnalysisMap);

		return trendModel;
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