package com.fire.stockmarkets.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.stockmarkets.dto.api.responses.StockDailyData;
import com.fire.stockmarkets.services.exchanges.HKEXService;
import com.fire.stockmarkets.services.exchanges.MOEXService;
import com.fire.stockmarkets.services.exchanges.USService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/data")
public class ApiController {
    @Autowired
    MOEXService moexService;

    @Autowired
    HKEXService hkexService;

    @Autowired
    USService usService;

    @GetMapping(value = "/moex/{symbol}")
    public StockDailyData getMOEXDataByCode(@PathVariable("symbol") String symbol) throws JsonProcessingException {
        return moexService.getStockDailyData(symbol, 5000L);
    }

    @GetMapping(value = "/hkex/{symbol}")
    public StockDailyData getHKEXDataByCode(@PathVariable("symbol") String symbol) throws JsonProcessingException {
        return hkexService.getStockDailyData(symbol, 5000L);
    }

    @GetMapping(value = "/us/{symbol}")
    public StockDailyData getUSDataByCode(@PathVariable("symbol") String symbol) throws JsonProcessingException {
        return usService.getStockDailyData(symbol, 5000L);
    }
}
