package com.aolangtech.nsignalweb.services;

import java.util.List;

import com.aolangtech.nsignalweb.models.OptionTradeModel;

public interface OptionTradeService {
	List<OptionTradeModel> getTradesByEventDay(Integer eventDay, String stockSymbol, Integer expiration, Character callPut, Double strike);

	List<OptionTradeModel> getTradeLegs(OptionTradeModel trade);
}
