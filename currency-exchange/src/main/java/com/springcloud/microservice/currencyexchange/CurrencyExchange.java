package com.springcloud.microservice.currencyexchange;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchange {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "currency_from")
	private String from;
	@Column(name = "currency_to")
	private String to;
	private BigDecimal conversionvalue;
	private String port;

	public CurrencyExchange(String from, String to, BigDecimal conversionvalue, String port) {
		super();
		this.from = from;
		this.to = to;
		this.port = port;
		this.conversionvalue = conversionvalue;
	}

}