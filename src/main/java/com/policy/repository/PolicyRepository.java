package com.policy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.entity.Policy;



@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {

	public List<Policy> findAllByStatus(String status);

	public Optional<Policy> findByPolicyIdAndStatus(String policyId, String string);

}
