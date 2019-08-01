package com.policy.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_policy")
public class UserPolicy implements Serializable{

	private static final long serialVersionUID = -3730909047319891889L;
	
	@Id
	private String userPolicyId;
	private String userId;
	private String policyId;
	private LocalDate dateOfPurchase;
	private String status;

}
