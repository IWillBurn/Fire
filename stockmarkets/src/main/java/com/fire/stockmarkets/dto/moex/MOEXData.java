package com.fire.stockmarkets.dto.moex;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MOEXData {
    private Map<String, Map<String, MOEXMetaData>> metadata;
    private String[] columns;
    private List<List<String>> data;
}
