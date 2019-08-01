package com.policy.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
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
import com.policy.entity.Policy;
import com.policy.entity.User;
import com.policy.entity.UserPolicy;
import com.policy.exception.PolicyException;
import com.policy.model.PolicyModel;
import com.policy.model.UserPolicyModel;
import com.policy.repository.UserPolicyRepository;
import com.policy.repository.UserRepository;
import com.policy.service.PolicyService;
import com.policy.service.UserService;

@Service
public class UserServiceImpl implements UserService 
{	
	@Autowired
	PolicyService policyService;
	
	@Autowired
	UserPolicyRepository userPolicyRepository;

	@Autowired
	UserRepository userRepository;
	
	@Override
	public ByteArrayInputStream pdfUserPolicyFileReport(List<UserPolicyModel> userPolicyModelList)
			throws DocumentException, PolicyException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 3,  3, 3, 3 });

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		List<String> pdfHeaderList = new ArrayList<>();
		pdfHeaderList.add("Policy Id");
		pdfHeaderList.add("User Name");
		pdfHeaderList.add("Policy Name");
		pdfHeaderList.add("Policy Type");
		pdfHeaderList.add("Date Of Purchase");
		
		for(String pdfHeader : pdfHeaderList )
		{
			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase(pdfHeader, headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
		}
		
		

		for (UserPolicyModel userPolicyModel : userPolicyModelList) {

			List<String> pdfDataList = new ArrayList<>();
			
			
			PdfPCell cell;

			String policyId = userPolicyModel.getPolicyId();
			
			PolicyModel policyDetails = policyService.policyDetails(policyId);
			
			String policyName = policyDetails.getPolicyName();
			LocalDate dateOfPurchase = userPolicyModel.getDateOfPurchase();
			String type = policyDetails.getType();
			
			String userId = userPolicyModel.getUserId();
			User user = getUser(userId);
			String userName = user.getUserName();
			
			pdfDataList.add(policyId);
			pdfDataList.add(userName);
			pdfDataList.add(policyName);
			pdfDataList.add(type);
			pdfDataList.add(dateOfPurchase.toString());
			
			for(String pdfColumn : pdfDataList)
			{
				cell = new PdfPCell(new Phrase(pdfColumn));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
			}

			
		}

		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);

		document.close();

		return new ByteArrayInputStream(out.toByteArray());

	}

	
	@Override
	public UserPolicyModel purchasePolicy(String userId, String policyId, Boolean tnc) throws PolicyException 
	{
		
		if(tnc)
		{
			UserPolicy savedPolicy;

			UserPolicyModel userPolicyModel = new UserPolicyModel();
			
			User user = getUser(userId);
			Policy policy = policyService.getPolicy(policyId);
			if(validateUserApplicableForPolicy(user,policy))
			{
				UserPolicy userPolicy = new UserPolicy();
				userPolicy.setUserPolicyId(createUserPolicyId(user,policy));
				userPolicy.setUserId(user.getUserId());
				userPolicy.setPolicyId(policy.getPolicyId());
				userPolicy.setDateOfPurchase(LocalDate.now());
				userPolicy.setStatus("ACTIVE");
				savedPolicy = userPolicyRepository.save(userPolicy);
				
				BeanUtils.copyProperties(savedPolicy, userPolicyModel);
				return userPolicyModel;
			}
			else
				throw new PolicyException("Hi, "+user.getUserName()+" your age is not applicable for policy "+policy.getPolicyName());	
		}
		else
			throw new PolicyException("Please checked all Terms & Conditions");
	
	}

	private String createUserPolicyId(User user, Policy policy) 
	{
		return user.getUserId()+"-"+policy.getPolicyId();

	}


	private boolean validateUserApplicableForPolicy(User user, Policy policy) 
	{
		String ageLimit = policy.getAgeLimit();
		Integer userAge = Period.between(user.getDob(), LocalDate.now()).getYears();
		
		List<String> ageRange = Stream.of(ageLimit.split("-"))
		.map(elem -> new StringBuilder(elem).toString())
		.collect(Collectors.toList());
		return (userAge > Integer.valueOf(ageRange.get(0)) && userAge < Integer.valueOf(ageRange.get(1)));
	}

	@Override
	public User getUser(String userId) throws PolicyException {
		
		Optional<User> findByIdOptional = userRepository.findById(userId);
		
		Boolean isOptionalPresent = findByIdOptional.isPresent();
		
		if(isOptionalPresent)
			return findByIdOptional.get();
		else
			throw new PolicyException("User Not Found with Id : "+userId);
	}

}
