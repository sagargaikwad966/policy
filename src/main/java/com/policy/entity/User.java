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
@Table(name = "user")
public class User implements Serializable{
	
	private static final long serialVersionUID = -3092606122605694487L;
	
	@Id
	private String userId;
	private String userName;
	private String password;
	private LocalDate dob;
	private String email;
	private String phone;
	private String status;
}
