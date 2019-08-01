package com.policy.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.entity.UserPolicy;
import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;
import com.policy.repository.UserPolicyRepository;
import com.policy.service.UserPolicyService;

@Service
public class UserPolicyServiceImpl implements UserPolicyService {

	@Autowired
	UserPolicyRepository userPolicyRepository;

	@Override
	public List<UserPolicyModel> getUserPolicyByUserId(String UserId) throws PolicyException {

		List<UserPolicyModel> userPolicyModels = new ArrayList<>();

		List<UserPolicy> userPolicies = new ArrayList<UserPolicy>();

		Optional<List<UserPolicy>> userPoliciesOptional = userPolicyRepository.findByUserId(UserId);

		boolean isOptionalPresent = userPoliciesOptional.isPresent();

		if (isOptionalPresent) {

			userPolicies = userPoliciesOptional.get();
			userPolicies.stream().forEach(product -> {
				UserPolicyModel model = new UserPolicyModel();

				BeanUtils.copyProperties(product, model);

				userPolicyModels.add(model);

			});
		} else {
			throw new PolicyException("No Policy available !!!");
		}
		return userPolicyModels;
	}
}
