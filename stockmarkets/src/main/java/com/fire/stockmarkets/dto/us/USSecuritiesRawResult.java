package com.fire.stockmarkets.dto.us;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class USSecuritiesRawResult {
    private Map<String, String> metadata;
    private Map<String, USData> rawData;

    @JsonCreator
    public USSecuritiesRawResult(@JsonProperty("Meta Data") Map<String, String> _metadata, @JsonProperty("Time Series (Daily)") Map<String, USData> _rawData){
        metadata = _metadata;
        rawData = _rawData;
    }
}
