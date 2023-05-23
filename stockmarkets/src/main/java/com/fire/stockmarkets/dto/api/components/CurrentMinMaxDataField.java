package com.fire.stockmarkets.dto.api.components;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentMinMaxDataField {
    private Float minValue;
    private Float maxValue;
}
