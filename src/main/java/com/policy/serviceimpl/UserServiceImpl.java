package com.policy.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.entity.Policy;
import com.policy.entity.User;
import com.policy.entity.UserPolicy;
import com.policy.exception.PolicyException;
import com.policy.model.UserPolicyModel;
import com.policy.repository.UserPolicyRepository;
import com.policy.repository.UserRepository;
import com.policy.service.PolicyService;
import com.policy.service.UserService;

@Service
public class UserServiceImpl implements UserService 
{
	public static Integer id = 1;
	
	@Autowired
	PolicyService policyService;
	
	@Autowired
	UserPolicyRepository userPolicyRepository;

	@Autowired
	UserRepository userRepository;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public UserPolicyModel purchasePolicy(String userId, String policyId, Boolean tnC) throws PolicyException 
	{
		
		if(tnC)
		{
			UserPolicy savedPolicy = new UserPolicy();
			UserPolicyModel userPolicyModel = new UserPolicyModel();
			
			User user = getUser(userId);
			Policy policy = policyService.getPolicy(policyId);
			if(validateUserApplicableForPolicy(user,policy))
			{
				UserPolicy userPolicy = new UserPolicy();
				userPolicy.setUserPolicyId(createUserPolicyId(user,policy));
				userPolicy.setUserId(user.getUserId());
				userPolicy.setPolicyId(policy.getPolicyId());
				userPolicy.setDateOfPurchase(LocalDate.now());
				userPolicy.setStatus("ACTIVE");
				savedPolicy = userPolicyRepository.save(userPolicy);
				
				BeanUtils.copyProperties(savedPolicy, userPolicyModel);
				return userPolicyModel;
			}
			else
				throw new PolicyException("Hi, "+user.getUserName()+" your age is not applicable for policy "+policy.getPolicyName());	
		}
		else
			throw new PolicyException("Please checked all Terms & Conditions");
	
	}







































































































































































	private String createUserPolicyId(User user, Policy policy) 
	{
		return user.getUserId()+"-"+policy.getPolicyId()+"-"+id++;
	}







































































































































































	private boolean validateUserApplicableForPolicy(User user, Policy policy) 
	{
		String ageLimit = policy.getAgeLimit();
		Integer userAge = Period.between(user.getDob(), LocalDate.now()).getYears();
		
		List<String> ageRange = Stream.of(ageLimit.split("-"))
		.map(elem -> new String(elem))
		.collect(Collectors.toList());
		
		if(userAge > Integer.valueOf(ageRange.get(0)) && userAge < Integer.valueOf(ageRange.get(1)))
			return true;
		else
			return false;
	}





























































	@Override
	public User getUser(String userId) throws PolicyException {
		
		Optional<User> findByIdOptional = userRepository.findById(userId);
		
		Boolean isOptionalPresent = findByIdOptional.isPresent();
		
		if(isOptionalPresent)
			return findByIdOptional.get();
		else
			throw new PolicyException("User Not Found with Id : "+userId);
	}

}
