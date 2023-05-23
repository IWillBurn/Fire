package com.fire.stockmarkets.dto.dt;

import lombok.Data;

@Data
public class TDExchangeValue {
    String symbol;
    Float rate;
    Long timestamp;
}
