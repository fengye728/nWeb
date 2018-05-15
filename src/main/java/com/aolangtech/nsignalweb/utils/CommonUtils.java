/**
 * 
 */
/**
 * @author SEELE
 *
 */
package com.aolangtech.nsignalweb.utils;

import org.apache.log4j.Logger;

import com.aolangtech.nsignalweb.models.OptionTradeModel;

public class CommonUtils {
	
	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(CommonUtils.class);
	

	/**
	 * Get the quarter of the event day.
	 * @param eventDay yyMMdd
	 * @return
	 */
	public static int getQuarter(Integer eventDay) {
		int yy = eventDay / 10000;
		int MM = eventDay % 10000 / 100;
		int q = MM / 4 + 1;
		return yy * 10 + q;
	}
	
	public static int increaseQuarter(int quarter) {
		return (quarter / 10 + ( (quarter + 1) % 10 / 5) ) * 10 + (quarter % 10 % 4 + 1);
	}
	
	public static int optionDate2StockDate(Integer date) {
		return 20000000 + date;
	}
	/**
	 * Check if the trade1 and trade2 is same option.
	 * @param trade1
	 * @param trade2
	 * @return
	 */
	public static boolean isSameOption(OptionTradeModel trade1, OptionTradeModel trade2) {
		if(trade1 == null || trade2 == null) {
			return true;
		}
		if(trade1.getStockSymbol().equals(trade2.getStockSymbol()) && trade1.getExpiration().equals(trade2.getExpiration()) 
				&& trade1.getCallPut().equals(trade2.getCallPut()) && trade1.getStrike().equals(trade2.getStrike())) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getFileSuffix(String name){
		return name.substring(name.lastIndexOf(".") + 1);
	}	
}