package com.fire.stockmarkets.services.exchanges;

import com.fire.stockmarkets.database.Market;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.DataField;

import java.util.List;

public interface StockMarketDataService {
    Boolean haveStock(String symbol);
    CompanyData getCompanyData(String symbol);
    List<DataField> getDailyData(String symbol, Long count);
    List<DataField> getHourData(String symbol, Long count);
    Float getCurrentData(String symbol, Market market);
}

