package com.fire.stockmarkets.services;

import com.fire.stockmarkets.dto.api.components.StockMarketData;
import org.springframework.stereotype.Service;

@Service
public class StockMarketService {
    public static StockMarketData getStockMarketData(String country, String stockMarket){
        return new StockMarketData(country, stockMarket);
    }
}
