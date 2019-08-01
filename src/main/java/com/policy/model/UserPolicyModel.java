package com.policy.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPolicyModel {
	
	private String userPolicyId;
	private String userId;
	private String policyId;
	private LocalDate dateOfPurchase;

}
