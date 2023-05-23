package com.fire.stockmarkets.dto.dt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TDStockSearchResult {
    private List<TDFindMetaData> data;
    private String status;

    TDStockSearchResult(){
        data = new ArrayList<>();
        status = "";
    }
}
