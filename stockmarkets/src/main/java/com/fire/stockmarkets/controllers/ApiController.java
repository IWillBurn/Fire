package com.fire.stockmarkets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.stockmarkets.database.Company;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.responses.CurrentStockResult;
import com.fire.stockmarkets.dto.api.responses.MinMaxStocksResult;
import com.fire.stockmarkets.dto.api.responses.StockData;
import com.fire.stockmarkets.dto.api.responses.StockMetaResult;
import com.fire.stockmarkets.services.DataService;
import com.fire.stockmarkets.services.exchanges.MOEXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    DataService dataService;
    @Autowired
    MOEXService moexService;

    @GetMapping(value = "/v1/data/search")
    public List<CompanyData> searchStocks(@RequestParam() String query, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageFrom, @RequestParam(required = false) Integer pageTo) throws JsonProcessingException {
        Integer from = 0;
        Integer to = 10;
        if (pageSize != null) {
            from = (pageFrom * pageSize);
            to = ((pageTo + 1) * pageSize - 2);
        }
        List<CompanyData> result = new ArrayList<>();
        List<Company> findResult = dataService.findCompaniesByString(query, from, to);
        for (Company company : findResult){
            result.add(new CompanyData(company.getSymbol(), company.getName(), company.getDescription()));
        }
        return result;
    }

    @GetMapping(value = "/v1/data/stock/{symbol}")
    public StockMetaResult getMetaData(@PathVariable("symbol") String symbol, @RequestParam String currency) throws JsonProcessingException {
        return dataService.getCompanyData(symbol, currency);
    }

    @GetMapping(value = "/v1/data/stock/{symbol}/interval")
    public StockData getIntervalData(@PathVariable("symbol") String symbol, @RequestParam String market, @RequestParam String interval, @RequestParam Long count) throws JsonProcessingException {
        return dataService.getIntervalData(symbol, market, interval, count);
    }

    @GetMapping(value = "/v1/data/stock/{symbol}/min-max")
    public MinMaxStocksResult getMinMaxCurrentData(@PathVariable("symbol") String symbol, @RequestParam String currency) throws JsonProcessingException {
        return dataService.getMinMaxCurrentData(symbol, currency);
    }
    @GetMapping(value = "/v1/data/stock/{symbol}/current")
    public CurrentStockResult getCurrentData(@PathVariable("symbol") String symbol, @RequestParam String market){
        return dataService.getCurrentData(symbol, market);
    }
    @GetMapping(value = "/v1/data/currencies")
    public List<CurrencyData> getAllCurrencies(){
        return dataService.getAllCurrencies();
    }

}
