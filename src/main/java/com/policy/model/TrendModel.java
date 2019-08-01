package com.policy.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendModel {

	private Double totalNoOfPoliciesSold;

	private Map policyAnalysisMap;

}
