package com.fire.stockmarkets.dto.api.responses;

import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.StockMarketData;
import com.fire.stockmarkets.dto.api.components.ValueData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockDailyData {
    private StockMarketData market;
    private CompanyData company;
    private CurrencyData currency;
    private List<ValueData> data;
}
