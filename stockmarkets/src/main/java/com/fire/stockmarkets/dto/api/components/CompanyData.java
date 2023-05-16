package com.fire.stockmarkets.dto.api.components;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyData {
    private String symbol;
    private String name;
    private String description;
}
