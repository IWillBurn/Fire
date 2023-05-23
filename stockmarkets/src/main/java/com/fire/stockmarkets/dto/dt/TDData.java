package com.fire.stockmarkets.dto.dt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDData {
    private String datetime;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private String volume;
}
