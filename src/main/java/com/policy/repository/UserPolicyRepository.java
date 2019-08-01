package com.policy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.policy.entity.UserPolicy;

@Repository
public interface UserPolicyRepository extends JpaRepository<UserPolicy, String> {

	Optional<List<UserPolicy>> findByUserId(String userId);

	@Query(value = "Select count(*) from User_policy where date_of_purchase between :date and :currentDate and status = 'ACTIVE'", nativeQuery=true)
	Double getTotalPoliciesCount(LocalDate date, LocalDate currentDate);

	@Query(value = "Select count(*) from User_policy where policy_Id = :policyId and date_of_purchase between :date and :currentDate and status = 'ACTIVE'", nativeQuery=true)
	Double getPolicyCount(String policyId, LocalDate date, LocalDate currentDate);
}
