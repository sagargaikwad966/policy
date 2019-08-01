package com.policy.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserPolicyModel implements Serializable{
	
	private static final long serialVersionUID = -7022670664815150567L;
	private String userPolicyId;
	private String userId;
	private String policyId;
	private LocalDateTime dateOfPurchase;
	private String status;

}
