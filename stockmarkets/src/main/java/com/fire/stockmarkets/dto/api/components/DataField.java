package com.fire.stockmarkets.dto.api.components;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataField {
    private Long date;
    private Float openValue;
    private Float minValue;
    private Float maxValue;
    private Float closeValue;
}
