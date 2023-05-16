package com.fire.stockmarkets.dto.us;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class USCompanyData {
    private String symbol;
    private String name;
    private String description;
    private String exchange;
    private String country;
    @JsonCreator
    public USCompanyData(@JsonProperty("Symbol") String _symbol,
                         @JsonProperty("Name") String _name,
                         @JsonProperty("Description") String _description,
                         @JsonProperty("Exchange") String _exchange,
                         @JsonProperty("Country") String _country){
        symbol = _symbol;
        name = _name;
        description = _description;
        exchange = _exchange;
        country = _country;
    }
}
