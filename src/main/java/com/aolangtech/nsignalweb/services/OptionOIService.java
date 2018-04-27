package com.aolangtech.nsignalweb.services;

import java.util.List;

import com.aolangtech.nsignalweb.models.OptionOIModel;

public interface OptionOIService {

	OptionOIModel getOIByEventDay(Integer eventDay, String stockSymbol, Integer expiration, Character callPut, Double strike);
	
	List<OptionOIModel> getOIsByBetweenEventDay(Integer startEventDay, Integer endEventDay, String stockSymbol, Integer expiration, Character callPut, Double strike);

}
