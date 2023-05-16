package com.fire.stockmarkets.dto.moex;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MOEXCompanyData {
    private MOEXData description;
    private MOEXData boards;
}
