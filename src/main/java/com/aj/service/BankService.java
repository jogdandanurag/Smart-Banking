package com.aj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.aj.model.BankDetails;
import com.aj.model.Role;
import com.aj.model.User;
import com.aj.repo.BankRepository;
import com.aj.repo.UserRepository;
import com.aj.utils.DateUtil;
import com.aj.vo.BankDetailsVo;


@Service
public class BankService {
	
	private BankRepository bankRepo;
	
	private  UserRepository userRepo;

	public BankDetailsVo registerBank(BankDetailsVo bankRegister) {

		BankDetails bank = new BankDetails();
		
		BeanUtils.copyProperties(bankRegister, bank);
		
		if(bankRegister.getBankName()!= null|| bankRegister.getBankName().isEmpty()) {
			throw new RuntimeException("please enter bank name");
		}
		if(bankRegister.getUsername()!= null || bankRegister.getUsername().isEmpty()) {
			throw new RuntimeException("please enter username");
		}
		
		if(bankRegister.getPassword()!= null || bankRegister.getPassword().isEmpty()) {
			throw new RuntimeException("please enter valid password");
		}		
		if(bankRepo.existsByBankName(bankRegister.getBankName())) {
			throw new RuntimeException(bankRegister.getBankName()+ " Bank name already exists");
		}
		
		validateEmailFormat(bankRegister.getEmail());
		validatePasswordStrength(bankRegister.getPassword());
		
		BankDetails registerBank =bankRepo.save(bankRegister);
		
		User user = new User();
		
		user.setBankId(registerBank.getId());
		user.setEmail(registerBank.getEmail());
		user.setPassword(registerBank.getPassword());
		user.setUsername(registerBank.getUsername());
		user.setMobileNumber(registerBank.getMobilNumber());
	    user.setRole(Role.ROLE_BANK_ADMIN);
		
		
	     userRepo.save(user);
		
	       bankRegister.setCreatedAt(DateUtil.convertTimestampToString(bank.getCreatedAt()));
		   bankRegister.setRole(Role.ROLE_BANK_ADMIN.name());
		   
		   return bankRegister;
	}
	
	
	public BankDetails updateInstitute(Long id, BankDetails bankDetails) {
		try {
			Optional<BankDetails> optionalBankDetails = bankRepo.findById(id);
			if (!optionalBankDetails.isPresent()) {
				return null;
			}
			if (bankDetails.getBankName() != null) {
				bankDetails.setBankName(bankDetails.getBankName());
			}
			if (bankDetails.getAddressLine1() != null) {
				bankDetails.setAddressLine1(bankDetails.getAddressLine1());
			}
			if (bankDetails.getEmail() != null) {
				bankDetails.setEmail(bankDetails.getEmail());
			}
			if (bankDetails.getMobilNumber() != null) {
				bankDetails.setMobilNumber(bankDetails.getMobilNumber());
			}
			return bankRepo.save(bankDetails);
		} catch (Exception e) {
			System.err.println("Error updating institute: " + e.getMessage());
			throw new RuntimeException("Failed to update institute. Please try agaevin.");
		}
	}


	public String deleteBankDetails(Long id) {
		try {
			Optional<BankDetails> optionalBankDetails = bankRepo.findById(id);
			if (optionalBankDetails.isPresent()) {
				BankDetails bankDetails = optionalBankDetails.get();
				//bankDetails.setStatus("Disabled");
				bankRepo.save(bankDetails);
				return "Institude Deleted Successfully";
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println("Error while disabling institute with ID " + id + ": " + e.getMessage());
			throw new RuntimeException("Failed to disable institute. Please try again.");
		}
	}

	public List<BankDetails> getAllBank(BankDetails bankDetails) {
		try {
			return bankRepo.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch institute list: " + e.getMessage(), e);
		}
	}

	
	
	

		private void validateEmailFormat(String email) {
		    // Email format validation using a more precise regex.
		    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		    if (email == null || !email.matches(emailRegex)) {
		        throw new RuntimeException("Invalid email format.");
		    }
		}

		private void validatePasswordStrength(String password) {
		    // Ensure password is at least 8 characters long, contains uppercase, lowercase, digit, and special character.
		    if (password == null || password.length() < 8 || 
		        !password.matches(".*[A-Z].*") || // Contains at least one uppercase letter
		        !password.matches(".*[a-z].*") || // Contains at least one lowercase letter
		        !password.matches(".*\\d.*") ||   // Contains at least one digit
		        !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) { // Contains special character
		        throw new RuntimeException("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
		    }
		}

		public BankDetailsVo updateBank(Long id, BankDetails bankDetails) {
			// TODO Auto-generated method stub
			return null;
		}

}
