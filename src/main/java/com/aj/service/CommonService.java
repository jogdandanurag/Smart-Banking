package com.aj.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aj.vo.UserInfoDetails;

@Service
public class CommonService {

	public Long orginizationIdIdFromContext() {
		UserInfoDetails userDetails = (UserInfoDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userDetails.getOrginizationId();
	}

}
