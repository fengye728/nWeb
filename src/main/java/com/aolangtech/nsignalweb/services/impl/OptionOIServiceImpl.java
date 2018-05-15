package com.aolangtech.nsignalweb.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aolangtech.nsignalweb.constants.CommonConstants;
import com.aolangtech.nsignalweb.mappers.option.OptionOIMapper;
import com.aolangtech.nsignalweb.models.OptionOIModel;
import com.aolangtech.nsignalweb.services.OptionOIService;
import com.aolangtech.nsignalweb.utils.CommonUtils;

@Service
public class OptionOIServiceImpl implements OptionOIService {

	@Autowired
	OptionOIMapper optionOIMapper;
	
	@Override
	public OptionOIModel getOIByEventDay(Integer eventDay, String stockSymbol, Integer expiration, Character callPut,
			Double strike) {

		String tableName = combineTableName(CommonUtils.getQuarter(eventDay));
		return optionOIMapper.selectByEventDay(tableName, eventDay, stockSymbol, expiration, callPut, strike);
	}

	@Override
	public List<OptionOIModel> getOIsByBetweenEventDay(Integer startEventDay, Integer endEventDay, String stockSymbol,
			Integer expiration, Character callPut, Double strike) {
		int startQuarter = CommonUtils.getQuarter(startEventDay);
		int endQuarter = CommonUtils.getQuarter(endEventDay);
		
		List<OptionOIModel> result = new ArrayList<>();
		
		int curQuarter = startQuarter;
		while(curQuarter <= endQuarter) {
			
			result.addAll(optionOIMapper.selectByBetweenEventDay(combineTableName(curQuarter), startEventDay, endEventDay, stockSymbol, expiration, callPut, strike));
			
			// loop
			curQuarter = CommonUtils.increaseQuarter(curQuarter);
		}
		return result;
	}
	
	private String combineTableName(int quarter) {
		return CommonConstants.OPTION_OI_TABLE_NAME_PREFIX + quarter;
	}

}
