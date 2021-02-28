package com.springcloud.microservice.currencyexchange;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController implements CommandLineRunner {

	@Autowired
	Environment env;

	@Autowired
	CurrencyExchangeDao dao;

	@GetMapping("/currency-exchange/from/{source}/to/{dist}")
	public CurrencyExchange getExhangeValue(@PathVariable String source, @PathVariable("dist") String dist) {

		return dao.findData(source, dist);

	}

	@Override
	public void run(String... args) throws Exception {

		String port = env.getProperty("server.port");
		CurrencyExchange currencyExchange1 = new CurrencyExchange("USD", "INR", BigDecimal.valueOf(65.00), port);
		CurrencyExchange currencyExchange2 = new CurrencyExchange("EUR", "INR", BigDecimal.valueOf(85.00), port);
		CurrencyExchange currencyExchange3 = new CurrencyExchange("YEN", "INR", BigDecimal.valueOf(15.00), port);

		dao.saveExchangeTypes(currencyExchange1);
		dao.saveExchangeTypes(currencyExchange2);
		dao.saveExchangeTypes(currencyExchange3);

	}

}
