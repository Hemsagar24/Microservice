package com.springcloud.microservice.currencyconversion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange", url = "localhost:8000")
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange/from/{source}/to/{dist}")
	public CurrencyConversion getExhangeValue(@PathVariable String source, @PathVariable("dist") String dist);

}
