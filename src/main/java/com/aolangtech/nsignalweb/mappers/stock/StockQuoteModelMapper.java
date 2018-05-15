package com.aolangtech.nsignalweb.mappers.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aolangtech.nsignalweb.models.StockQuoteModel;

public interface StockQuoteModelMapper {
    
    List<StockQuoteModel> selectBetweenBySymbol(@Param("symbol")String symbol, @Param("startDate") Integer startDate, @Param("endDate") Integer endDate);
    
}