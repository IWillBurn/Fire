package com.fire.stockmarkets.dto.api.components;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockMarketData {
    private String country;
    private String stockMarket;
}
