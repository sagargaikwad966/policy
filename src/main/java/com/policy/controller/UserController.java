package com.policy.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.policy.exception.PolicyException;
import com.policy.model.ResponseData;
import com.policy.model.UserPolicyModel;
import com.policy.service.UserPolicyService;
import com.policy.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	UserPolicyService userPolicyService;
	
	@Autowired
	UserService userService;

	@GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> userPolicyFileReport(@PathVariable("userId") String userId)
			throws PolicyException, DocumentException {

		List<UserPolicyModel> userPolicyModelList = userPolicyService.getUserPolicyByUserId(userId);

		ByteArrayInputStream bis = userService.pdfUserPolicyFileReport(userPolicyModelList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=userpolicyreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
	
	@PostMapping("/policy/purchase/{userId}")
	public ResponseEntity<ResponseData> purchasePolicy(@PathVariable("userId") String userId, @RequestParam("policyId") String policyId, @RequestParam("t&c") Boolean tnc) throws PolicyException
	{
		UserPolicyModel userPolicyModel = userService.purchasePolicy(userId, policyId, tnc);
		Map<Integer, String> status = new HashMap();
		status.put(200, "SUCCESSFULL TRANSACTION");
		ResponseData response = new ResponseData("Policy Purchased successfully", status, userPolicyModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
