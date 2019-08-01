package com.policy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyAnalysis {

	private String name;
	private Double numberOfPoliciesSold;
	private Double percentage;
}
