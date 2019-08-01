package com.policy.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.policy.entity.User;
import com.policy.exception.PolicyException;
import com.policy.model.PolicyModel;
import com.policy.model.UserPolicyModel;
import com.policy.service.GenerateUserPolicyFileReportService;
import com.policy.service.PolicyService;
import com.policy.service.UserService;

@Service
public class GenerateUserPolicyFileReportServiceImpl implements GenerateUserPolicyFileReportService {

	@Autowired
	PolicyService policyService;
	
	@Autowired
	UserService userService;

	@Override
	public ByteArrayInputStream pdfUserPolicyFileReport(List<UserPolicyModel> userPolicyModelList)
			throws DocumentException, PolicyException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 3,  3, 3, 3 });

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		PdfPCell hcell;
		hcell = new PdfPCell(new Phrase("Policy Id", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		
		hcell = new PdfPCell(new Phrase("User Name", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		hcell = new PdfPCell(new Phrase("Policy Name", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);
		
		hcell = new PdfPCell(new Phrase("Policy Type", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		hcell = new PdfPCell(new Phrase("Date Of Purchase", headFont));
		hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(hcell);

		for (UserPolicyModel userPolicyModel : userPolicyModelList) {

			PdfPCell cell;

			String policyId = userPolicyModel.getPolicyId();
			PolicyModel policyDetails = policyService.policyDetails(policyId);
			String policyName = policyDetails.getPolicyName();
			LocalDate dateOfPurchase = userPolicyModel.getDateOfPurchase();
			String type = policyDetails.getType();
			
			String userId = userPolicyModel.getUserId();
			User user = userService.getUser(userId);
			String userName = user.getUserName();

			cell = new PdfPCell(new Phrase(policyId));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(userName));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(policyName));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			cell = new PdfPCell(new Phrase(type));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(dateOfPurchase.toString()));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

		}

		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);

		document.close();

		return new ByteArrayInputStream(out.toByteArray());

	}

}
