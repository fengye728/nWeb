package com.aolangtech.nsignalweb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.models.StockQuoteModel;
import com.aolangtech.nsignalweb.services.StockService;
import com.aolangtech.nsignalweb.utils.CommonUtils;

@RestController
@RequestMapping(value = "/stock")
public class StockController {

	@Autowired
	StockService stockService;
	
	@RequestMapping(value = "/betweenQuote")
	public List<StockQuoteModel> betweenQuote(@RequestBody Map<String, String> valueMap) {
		String symbol = valueMap.get("stockSymbol");
		Integer startDate =CommonUtils.optionDate2StockDate(Integer.valueOf(valueMap.get("startEventDay")));
		Integer endDate = CommonUtils.optionDate2StockDate(Integer.valueOf(valueMap.get("endEventDay")));
		return stockService.betweenQuote(symbol, startDate, endDate);
	}
}
