package com.aolangtech.nsignalweb.services;

import java.util.List;

import com.aolangtech.nsignalweb.models.StockQuoteModel;

public interface StockService {

	List<StockQuoteModel> betweenQuote(String symbol, Integer startDate, Integer endDate); 
}
