package com.fire.stockmarkets.controllers;

import com.fire.stockmarkets.services.exchanges.TDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    TDService tdService;

    @PostMapping(value = "/init")
    public void initService() {
        tdService.initService();
    }

    @PostMapping(value = "/all")
    public void addAllStocksToDataBase() {
        tdService.addAllStocksToDataBase();
    }

    @PostMapping(value = "/markets")
    public void addAllMarketsToDataBase() {
        tdService.addAllMarketsToDataBase();
    }

    @PostMapping(value = "/markets_update")
    public void updateAllMarketsToDataBase() {
        tdService.updateMarketsCurrencies();
    }

    @PostMapping(value = "/stock_data/{symbol}")
    public void addStockDataToDataBase(@PathVariable("symbol") String symbol) {
        tdService.addIntervalData(symbol, "NASDAQ", "1h");
    }

}
