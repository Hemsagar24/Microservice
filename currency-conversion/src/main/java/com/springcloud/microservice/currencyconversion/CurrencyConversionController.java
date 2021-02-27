package com.springcloud.microservice.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private RestTemplate rest;

	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{source}/to/{dist}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String source, @PathVariable String dist) {

		BigDecimal quantity = new BigDecimal(req.getParameter("q"));
		HashMap<String, String> urivariables = new HashMap<String, String>();
		urivariables.put("source", source);
		urivariables.put("dist", dist);

		ResponseEntity<CurrencyConversion> exchangeResponse = rest.getForEntity(
				"http://localhost:8000/currency-exchange/from/{source}/to/{dist}", CurrencyConversion.class,
				urivariables);
		CurrencyConversion currencyConversion = exchangeResponse.getBody();
		BigDecimal conversionValue = currencyConversion.getConversionvalue();
		BigDecimal calculatedConversionAmount = conversionValue.multiply(quantity);

		return new CurrencyConversion(1001L, source, dist, quantity, conversionValue, calculatedConversionAmount,
				currencyConversion.getPort()+"-RestTemplate");
	}

	@GetMapping("/currency-conversion-feign/from/{source}/to/{dist}")
	public CurrencyConversion calculateCurrencyConversion_feign(@PathVariable String source,
			@PathVariable String dist) {

		BigDecimal quantity = new BigDecimal(req.getParameter("q"));

		CurrencyConversion currencyConversion = proxy.getExhangeValue(source, dist);

		BigDecimal conversionValue = currencyConversion.getConversionvalue();
		BigDecimal calculatedConversionAmount = conversionValue.multiply(quantity);

		return new CurrencyConversion(1001L, source, dist, quantity, conversionValue, calculatedConversionAmount,
				currencyConversion.getPort()+"-feign");
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}