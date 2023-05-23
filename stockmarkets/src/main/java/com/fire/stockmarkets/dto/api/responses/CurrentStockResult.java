package com.fire.stockmarkets.dto.api.responses;

import com.fire.stockmarkets.database.Currency;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentStockResult {
    private Float price;
    private CurrencyData currency;
}
