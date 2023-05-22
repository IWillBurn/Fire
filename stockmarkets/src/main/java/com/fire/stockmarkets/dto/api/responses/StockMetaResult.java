package com.fire.stockmarkets.dto.api.responses;

import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.CurrentMinMaxDataField;
import com.fire.stockmarkets.dto.api.components.MarketWithCurrentData;
import com.fire.stockmarkets.dto.api.components.StockMarketData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockMetaResult {
    private String symbol;
    private String name;
    private String description;
    private CurrencyData requiredCurrency;
    private CurrentMinMaxDataField value;
    private List<MarketWithCurrentData> markets;
}
