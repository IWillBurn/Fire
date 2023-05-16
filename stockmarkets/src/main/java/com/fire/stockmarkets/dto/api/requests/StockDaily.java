package com.fire.stockmarkets.dto.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDaily {
    private String symbol;
}
