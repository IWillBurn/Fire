package com.fire.stockmarkets.dto.dt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TDFindMetaData {
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    private String mic_code;
    private String country;
    private String type;
}
