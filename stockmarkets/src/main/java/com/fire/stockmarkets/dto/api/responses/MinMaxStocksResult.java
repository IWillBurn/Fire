package com.fire.stockmarkets.dto.api.responses;

import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.CurrentMinMaxDataField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinMaxStocksResult {
    private CurrentMinMaxDataField value;
    private CurrencyData currency;
}
