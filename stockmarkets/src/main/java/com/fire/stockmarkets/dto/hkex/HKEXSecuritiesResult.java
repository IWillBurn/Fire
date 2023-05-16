package com.fire.stockmarkets.dto.hkex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HKEXSecuritiesResult {
    private HKEXData datasetData;

    @JsonCreator
    public HKEXSecuritiesResult(@JsonProperty("dataset_data") HKEXData _datasetData){
        datasetData = _datasetData;
    }
}
