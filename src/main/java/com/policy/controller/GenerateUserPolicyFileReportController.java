package com.policy.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.model.UserPolicyModel;
import com.policy.service.GenerateUserPolicyFileReportService;
import com.policy.service.UserPolicyService;

@RestController
@RequestMapping("/user")
public class GenerateUserPolicyFileReportController {

	@Autowired
	GenerateUserPolicyFileReportService GenerateUserPolicyFileReportService;

	@Autowired
	UserPolicyService userPolicyService;

	@GetMapping("/{id}")
	public ResponseEntity<InputStreamResource> UserPolicyFileReport(@PathVariable("id") String userId)
			throws Exception {

		List<UserPolicyModel> userPolicyModelList = userPolicyService.getUserPolicyByUserId(userId);

		ByteArrayInputStream bis = GenerateUserPolicyFileReportService.pdfUserPolicyFileReport(userPolicyModelList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=userpolicyreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

}
