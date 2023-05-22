package com.fire.stockmarkets.dto.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchStocks {
    private Integer pageSize;
    private Integer pageFrom;
    private Integer pageTo;
}
