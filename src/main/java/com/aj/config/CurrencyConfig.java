package com.aj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConfig {

	
	@Value("${smart-banking.currency}")
    private String defaultCurrency;

    public String getDefaultCurrency() {
        return defaultCurrency;
    }
	
}
