package com.fire.stockmarkets.dto.api.components;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketWithCurrentData {
    private StockMarketData market;
    private CurrencyData currency;
    private Float price;
    private Float priceExchanged;
}
