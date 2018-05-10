package com.aolangtech.nsignalweb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aolangtech.nsignalweb.models.OptionOIModel;
import com.aolangtech.nsignalweb.services.OptionOIService;

@RestController
@RequestMapping(value = "/oi")
public class OpenInterestController extends BaseController {
	
	@Autowired
	OptionOIService optionOIService;

	@PostMapping("/between")
	public List<OptionOIModel> getBetweenOI(@RequestBody Map<String, String> searchMap) {
		String stockSymbol = searchMap.get("stockSymbol");
		String callPut = searchMap.get("callPut");
		Double strike = Double.valueOf(searchMap.get("strike"));
		Integer expiration = Integer.valueOf(searchMap.get("expiration"));
		Integer startEventDay = Integer.valueOf(searchMap.get("startEventDay"));
		Integer endEventDay = Integer.valueOf(searchMap.get("endEventDay"));
		
		return optionOIService.getOIsByBetweenEventDay(startEventDay, endEventDay, stockSymbol, expiration, callPut.charAt(0), strike);
		
	}
}
