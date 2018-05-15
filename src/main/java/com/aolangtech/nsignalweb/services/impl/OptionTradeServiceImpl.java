package com.aolangtech.nsignalweb.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aolangtech.nsignalweb.constants.CommonConstants;
import com.aolangtech.nsignalweb.mappers.option.OptionTradeMapper;
import com.aolangtech.nsignalweb.models.OptionTradeModel;
import com.aolangtech.nsignalweb.services.OptionTradeService;
import com.aolangtech.nsignalweb.utils.CommonUtils;

@Service
//@Transactional(value = "transactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
public class OptionTradeServiceImpl implements OptionTradeService {
	
	@Autowired
	OptionTradeMapper optionTradeMapper;

	@Override
	public List<OptionTradeModel> getTradesByEventDay(Integer eventDay, String stockSymbol, Integer expiration,
			Character callPut, Double strike) {

		String tableName = combineTableName(CommonUtils.getQuarter(eventDay));
		
		return optionTradeMapper.selectByEventDay(tableName, eventDay, stockSymbol, expiration, callPut, strike);
	}

	@Override
	public List<OptionTradeModel> getTradeLegs(OptionTradeModel trade) {
		String tableName = combineTableName(CommonUtils.getQuarter(trade.getEventDay()));

		List<OptionTradeModel> result = new ArrayList<>();
		result.add(trade);

		OptionTradeModel newTrade = trade;
		boolean isDone = false;
		
		// trade leg's form like linked list
		while(!isDone) {
			if(newTrade.getLegSequenceId() == null) {
				break;
			} else {
				newTrade = optionTradeMapper.selectBySequenceId(tableName, newTrade.getLegSequenceId(), newTrade.getEventDay(), newTrade.getStockSymbol(), 
						newTrade.getReportExg());
				
				if(newTrade == null) {
					break;
				}
				
				// check if this trade is in result
				for( OptionTradeModel t : result) {
					if(t.getId().equals(trade.getId())) {
						isDone = true;
						break;
					}
				}
			}
			
		}
		
		return result;
	}
	
	private String combineTableName(int quarter) {
		return CommonConstants.OPTION_TRADE_TABLE_NAME_PREFIX + quarter;
	}

}
