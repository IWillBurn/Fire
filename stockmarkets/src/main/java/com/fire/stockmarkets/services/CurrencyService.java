package com.fire.stockmarkets.services;

import com.fire.stockmarkets.database.CurrenciesRepository;
import com.fire.stockmarkets.database.Currency;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    static
    CurrenciesRepository currenciesRepository;
    public static CurrencyData getRubleData(){ return new CurrencyData("RUB", "₽"); }
    public static CurrencyData getDollarData(){
        return new CurrencyData("USD", "$");
    }
    public static CurrencyData getHKDollarData(){
        return new CurrencyData("HKD", "HK$");
    }

    public static CurrencyData getCurrency(String currencyName){
        Currency currency = currenciesRepository.findByName(currencyName);
        return new CurrencyData(currency.getCurrency(), currency.getIcon());
    }
}
