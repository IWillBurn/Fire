package com.fire.stockmarkets.dto.api.components;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValueData {
    private String date;
    private String openValue;
    private String minValue;
    private String maxValue;
    private String closeValue;
}
