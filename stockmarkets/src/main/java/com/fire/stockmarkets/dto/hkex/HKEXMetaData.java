package com.fire.stockmarkets.dto.hkex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HKEXMetaData {
    private String datasetCode;
    private String name;

    @JsonCreator
    public HKEXMetaData(@JsonProperty("dataset_code") String _datasetCode,
                        @JsonProperty("name") String _name){
        datasetCode = _datasetCode;
        name = _name;
    }
}
