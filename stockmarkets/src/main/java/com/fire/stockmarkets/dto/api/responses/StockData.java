package com.fire.stockmarkets.dto.api.responses;

import com.fire.stockmarkets.database.Company;
import com.fire.stockmarkets.database.Currency;
import com.fire.stockmarkets.database.Market;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.StockMarketData;
import com.fire.stockmarkets.dto.api.components.DataField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StockData {
    private StockMarketData market;
    private CompanyData company;
    private CurrencyData currency;
    private List<DataField> data;

    public StockData(Market _market, Company _company, Currency _currency) {
        market = new StockMarketData(_market.getCountry(), _market.getStockMarket());
        company = new CompanyData(_company.getSymbol(), _company.getName(), _company.getDescription());
        currency = new CurrencyData(_currency.getCurrency(), _currency.getIcon());
    }
}
