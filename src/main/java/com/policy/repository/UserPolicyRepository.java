package com.policy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policy.entity.UserPolicy;

public interface UserPolicyRepository extends JpaRepository<UserPolicy, String> {

	Optional<List<UserPolicy>> findByUserId(String userId);

	
	
}
