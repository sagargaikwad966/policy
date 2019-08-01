package com.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.policy.entity.UserPolicy;

public interface UserPolicyRepository extends JpaRepository<UserPolicy, String> {

}
