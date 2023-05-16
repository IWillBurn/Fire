package com.fire.stockmarkets.dto.us;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class USSecuritiesResult {
    private List<USData> data;
}
