package com.aolangtech.nsignalweb.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aolangtech.nsignalweb.mappers.stock.StockQuoteModelMapper;
import com.aolangtech.nsignalweb.models.StockQuoteModel;
import com.aolangtech.nsignalweb.services.StockService;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	StockQuoteModelMapper stockQuoteModelMapper;
	
	@Override
	public List<StockQuoteModel> betweenQuote(String symbol, Integer startDate, Integer endDate) {
		if(symbol == null || startDate == null || endDate == null) {
			return null;
		}
		return stockQuoteModelMapper.selectBetweenBySymbol(symbol, startDate, endDate);
	}

}
