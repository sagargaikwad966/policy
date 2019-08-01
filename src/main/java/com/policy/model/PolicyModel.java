package com.policy.model;

import lombok.Data;

@Data
public class PolicyModel {

	private String policyId;
	private String policyName;
	private String ageLimit;
	private Double sumInsured;
	private Double premium;
	private Double totalPremium;
	private Integer maturityPeriod;
	private String type;

}
