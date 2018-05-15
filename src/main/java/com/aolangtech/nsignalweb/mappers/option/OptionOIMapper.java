package com.aolangtech.nsignalweb.mappers.option;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aolangtech.nsignalweb.models.OptionOIModel;

public interface OptionOIMapper {
	
	OptionOIModel selectByEventDay(@Param("tableName") String tableName, @Param("eventDay") Integer eventDay, @Param("stockSymbol") String stockSymbol, @Param("expiration") Integer expiration, @Param("callPut") Character callPut, @Param("strike") Double strike);
	
	List<OptionOIModel> selectByBetweenEventDay(@Param("tableName") String tableName, @Param("startEventDay") Integer startEventDay, @Param("endEventDay") Integer endEventDay,  @Param("stockSymbol") String stockSymbol, @Param("expiration") Integer expiration, @Param("callPut") Character callPut, @Param("strike") Double strike);
}