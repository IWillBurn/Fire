package com.fire.stockmarkets.dto.hkex;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HKEXData {
    private String limit;
    private String transform;
    private String columnIndex;
    private List<String> columnNames;
    private String startDate;
    private String endDate;
    private String frequency;
    private List<List<String>> data;
    private String collapse;
    private String order;
}
