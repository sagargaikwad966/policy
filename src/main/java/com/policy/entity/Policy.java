package com.policy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "policy")
public class Policy implements Serializable{
	
	private static final long serialVersionUID = 3454411908097530306L;
	
	@Id
	private String policyId;
	private String policyName;
	private String ageLimit;
	private Double sumInsured;
	private Double premium;
	private Double totalPremium;
	private Integer maturityPeriod;
	private String type;
	private String status;

}
