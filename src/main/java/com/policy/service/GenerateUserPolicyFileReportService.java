package com.policy.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.policy.model.UserPolicyModel;

@Service
public interface GenerateUserPolicyFileReportService {

	ByteArrayInputStream pdfUserPolicyFileReport(List<UserPolicyModel> userPolicyModelList) throws DocumentException;

}
