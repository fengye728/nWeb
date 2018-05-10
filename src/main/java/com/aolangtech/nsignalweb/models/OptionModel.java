package com.aolangtech.nsignalweb.models;

public class OptionModel {

	private String symbol;
	
	private Character callPut;
	
	private Integer expiration;
	
	private Double strike;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Character getCallPut() {
		return callPut;
	}

	public void setCallPut(Character callPut) {
		this.callPut = callPut;
	}

	public Integer getExpiration() {
		return expiration;
	}

	public void setExpiration(Integer expiration) {
		this.expiration = expiration;
	}

	public Double getStrike() {
		return strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}
}
