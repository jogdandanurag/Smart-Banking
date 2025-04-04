package com.aj.api;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aj.model.BankDetails;
import com.aj.model.Response;
import com.aj.service.BankService;
import com.aj.utils.ResponseConstant;
import com.aj.vo.BankDetailsVo;

@RestController
@RequestMapping("/api/bank")
public class BankController {

	
	private BankService bankService;
	
	
	
	@PostMapping("/registerBank")
	public Response<BankDetailsVo> createNewBank(@RequestBody BankDetailsVo bankRegister ){
		try {
		BankDetailsVo newBank=  bankService.registerBank(bankRegister);
		
		if(newBank != null) {
			return new Response<BankDetailsVo>(ResponseConstant.SUCCESS_CODE,
					   ResponseConstant.SUCCESS_MSG, 
					   newBank);
	}else {
		return new Response<BankDetailsVo>(ResponseConstant.DATABASE_SAVE_ERROR_CODE,
				ResponseConstant.DATABASE_SAVE_ERROR_MSG, 
				   newBank);
		}
	}catch(Exception e) {
		return new Response<BankDetailsVo>(ResponseConstant.UNKNOWN_ERROR_CODE, e.getMessage(),
				null);
	}
		
}
	
	
	
	@PutMapping("/update")
	@Secured(value = {"ROLE_ADMIN"})
	public Response<BankDetailsVo> updateBankDetails(@RequestParam("bankId") Long id, @RequestBody BankDetails bankDetails) {
		try {
			BankDetailsVo updateBankDetails= bankService.updateBank(id, bankDetails);
			if (updateBankDetails != null)
				return new Response<BankDetailsVo>(ResponseConstant.SUCCESS_CODE, ResponseConstant.SUCCESS_MSG,
						updateBankDetails);
			else {
				return new Response<BankDetailsVo>(ResponseConstant.RECORD_NOT_FOUND_CODE,
						ResponseConstant.RECORD_NOT_FOUND_MSG, null);
			}
		} catch (Exception e) {
			return new Response<BankDetailsVo>(ResponseConstant.INTERNAL_SERVER_ERROR_CODE,
					ResponseConstant.INTERNAL_SERVER_ERROR_MSG, null);
		}

	}

	@DeleteMapping("/delete")
	@Secured(value = {"ROLE_ADMIN"})
	public Response<String> deleteBank(@RequestParam("bankId") Long id) {
		try {
			String deleteBank = bankService.deleteBankDetails(id);
			if (deleteBank != null) {
				return new Response<String>(ResponseConstant.SUCCESS_CODE, ResponseConstant.SUCCESS_MSG,
						deleteBank);
			} else {
				return new Response<String>(ResponseConstant.RECORD_NOT_FOUND_CODE,
						ResponseConstant.RECORD_NOT_FOUND_MSG, null);
			}
		} catch (Exception e) {
			return new Response<String>(ResponseConstant.INTERNAL_SERVER_ERROR_CODE, e.getMessage(), null);
		}
	}

	// Get All Institute List
	@GetMapping("/list")
	@Secured(value = {"ROLE_ADMIN", })
	public Response<List<BankDetails>> getAllInstitute(BankDetails bankDetails) {
		try {
			List<BankDetails> allBank = bankService.getAllBank(bankDetails);
			if (!allBank.isEmpty()) {
				return new Response<List<BankDetails>>(ResponseConstant.SUCCESS_CODE, ResponseConstant.SUCCESS_MSG,
						allBank);
			} else {
				return new Response<List<BankDetails>>(ResponseConstant.RECORD_NOT_FOUND_CODE,
						ResponseConstant.RECORD_NOT_FOUND_MSG, null);
			}
		} catch (Exception e) {
			return new Response<List<BankDetails>>(ResponseConstant.INTERNAL_SERVER_ERROR_CODE, e.getMessage(), null);
		}
	}
	
	
	
}