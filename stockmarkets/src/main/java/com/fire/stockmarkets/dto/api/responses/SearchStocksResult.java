package com.fire.stockmarkets.dto.api.responses;

import com.fire.stockmarkets.database.Company;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchStocksResult {
    private List<CompanyData> result;
}
