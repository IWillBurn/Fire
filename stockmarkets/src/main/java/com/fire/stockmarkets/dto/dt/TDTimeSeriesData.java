package com.fire.stockmarkets.dto.dt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TDTimeSeriesData {
    private TDTimeSeriesMetaData meta;
    private List<TDData> values;
    private String status;
}
