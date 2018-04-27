/**
 * 
 */
/**
 * @author AOLANG
 *
 */
package com.aolangtech.nsignalweb.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aolangtech.nsignalweb.models.OptionTradeModel;

public interface OptionTradeMapper {
	
	List<OptionTradeModel> selectByEventDay(@Param("tableName") String tableName, @Param("eventDay") Integer eventDay, @Param("stockSymbol") String stockSymbol, @Param("expiration") Integer expiration, @Param("callPut") Character callPut, @Param("strike") Double strike);
	
	OptionTradeModel selectBySequenceId(@Param("tableName") String tableName, @Param("sequenceId") Long sequeceId,@Param("eventDay") Integer eventDay, @Param("stockSymbol") String stockSymbol, @Param("reportExg") Short reportExg);
}