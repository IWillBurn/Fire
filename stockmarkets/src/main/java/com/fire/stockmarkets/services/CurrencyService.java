package com.fire.stockmarkets.services;

import com.fire.stockmarkets.dto.api.components.CurrencyData;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    public static CurrencyData getRubleData(){ return new CurrencyData("RUB", "₽"); }
    public static CurrencyData getDollarData(){
        return new CurrencyData("USD", "$");
    }
    public static CurrencyData getHKDollarData(){
        return new CurrencyData("HKD", "HK$");
    }
}
