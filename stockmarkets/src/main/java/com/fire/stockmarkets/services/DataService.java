package com.fire.stockmarkets.services;

import com.fire.stockmarkets.database.*;
import com.fire.stockmarkets.dto.api.components.*;
import com.fire.stockmarkets.dto.api.responses.CurrentStockResult;
import com.fire.stockmarkets.dto.api.responses.MinMaxStocksResult;
import com.fire.stockmarkets.dto.api.responses.StockData;
import com.fire.stockmarkets.dto.api.responses.StockMetaResult;
import com.fire.stockmarkets.services.exchanges.TDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    CompaniesRepository companiesRepository;
    @Autowired
    CurrenciesRepository currenciesRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    MarketsRepository marketsRepository;

    @Autowired
    TDService tdService;

    public List<Company> findCompaniesByString(String input, Integer from, Integer to){
        List<Company> resultRaw = new ArrayList<>();

        if (to > resultRaw.size()) {
            List<Company> completeMatchesSymbol = companiesRepository.findCompaniesBySymbolLikeString(input, "#####", "#####");
            mergeLists(resultRaw, completeMatchesSymbol);
        }

        if (to > resultRaw.size()) {
            List<Company> completeMatchesName = companiesRepository.findCompaniesByNameLikeString(input, input, "#####");
            mergeLists(resultRaw, completeMatchesName);
        }

        if (to > resultRaw.size()) {
            List<Company> prefixMatchesSymbol = companiesRepository.findCompaniesBySymbolLikeString(input + "%", input, input);
            mergeLists(resultRaw, prefixMatchesSymbol);
        }

        if (to > resultRaw.size()) {
            List<Company> prefixMatchesName = companiesRepository.findCompaniesByNameLikeString(input + "%", input + "%", input);
            mergeLists(resultRaw, prefixMatchesName);
        }

        if (to > resultRaw.size()) {
            String likeString = completeLikeString(input);
            List<Company> OtherMatches = companiesRepository.findCompaniesByLikeString(likeString, input + "%", input + "%");
            mergeLists(resultRaw, OtherMatches);
        }

        if (from > resultRaw.size()){
            return new ArrayList<>();
        }

        if (from > resultRaw.size()) {
            for (Integer i = 0; i < from; i++) {
                resultRaw.remove(0);
            }
        }

        if (to > resultRaw.size()){
            return resultRaw;
        }

        Integer toDelete = resultRaw.size() - 1 - (to - from + 1);

        for (Integer i = 0; i < toDelete; i++){
            resultRaw.remove(to - from + 2);
        }

        return resultRaw;
    }

    public StockData getIntervalData(String symbol, String marketName, String interval, Long count){
        StockData data = tdService.addIntervalData(symbol, marketName, interval);
        List<Data> searchResult = dataRepository.findDataBySymbolAndMarketInType(symbol, marketName, interval, count);
        List<DataField> result = new ArrayList<>();
        Market market = marketsRepository.findByName(marketName);
        for (Data field : searchResult){
            result.add(new DataField(Timestamp.valueOf(DateService.toUnixStandard(field.getDate())).getTime(), field.getOpenValue(), field.getMinValue(), field.getMaxValue(), field.getCloseValue()));
        }
        data.setData(result);
        return data;
    }

    public MinMaxStocksResult getMinMaxCurrentData(String symbol, String currencyName){
        Company company = companiesRepository.findBySymbol(symbol);
        Currency currency = currenciesRepository.findByName(currencyName);
        Float maxValue = -1000000000f;
        Float minValue = 1000000000f;
        for (Market market : company.getMarkets()){
            Float exchange = tdService.getExchange(market.getCurrency(), currency);
            if (market.getCurrency().getCurrency() == currency.getCurrency()){
                exchange = 1f;
            }
            if (exchange != null) {
                Float value = tdService.getCurrentData(symbol, market);
                if (value != null) {
                    value *= exchange;
                    if (value > maxValue) { maxValue = value; }
                    if (value < minValue) { minValue = value; }
                }
            }
        }
        if (maxValue == -1000000000d){
            maxValue = null;
        }
        if (minValue == 1000000000d){
            minValue = null;
        }
        return new MinMaxStocksResult(new CurrentMinMaxDataField(minValue, maxValue), new CurrencyData(currency.getCurrency(), currency.getIcon()));
    }

    public CurrentStockResult getCurrentData(String symbol, String marketName){
        Market market = marketsRepository.findByName(marketName);
        Float result = tdService.getCurrentData(symbol, market);
        Currency currency = market.getCurrency();
        return new CurrentStockResult(result, new CurrencyData(currency.getCurrency(), currency.getIcon()));
    }

    public StockMetaResult getCompanyData(String symbol, String currencyName){
        Company company = companiesRepository.findBySymbol(symbol);
        Currency currency = currenciesRepository.findByName(currencyName);
        List<Market> markets = company.getMarkets();

        List<MarketWithCurrentData> marketsWithData = new ArrayList<>();

        Float maxValue = -1000000000f;
        Float minValue = 1000000000f;
        for (Market market : markets){
            Currency mainCurrency = market.getCurrency();
            Float exchange = tdService.getExchange(mainCurrency, currency);
            Float value = tdService.getCurrentData(symbol, market);
            Float exchangedValue = null;
            if (market.getCurrency().getCurrency() == currency.getCurrency()){ exchange = 1f; }
            if (exchange != null && value != null) {
                exchangedValue = value * exchange;
                if (exchangedValue > maxValue) { maxValue = exchangedValue; }
                if (exchangedValue < minValue) { minValue = exchangedValue; }
            }
            marketsWithData.add(new MarketWithCurrentData(new StockMarketData(market.getCountry(), market.getStockMarket()), new CurrencyData(mainCurrency.getCurrency(), mainCurrency.getIcon()), value, exchangedValue));
        }
        if (maxValue == -1000000000d){
            maxValue = null;
        }
        if (minValue == 1000000000d){
            minValue = null;
        }
        return new StockMetaResult(company.getSymbol(), company.getName(), company.getDescription(), new CurrencyData(currency.getCurrency(), currency.getIcon()), new CurrentMinMaxDataField(minValue, maxValue), marketsWithData);
    }

    public List<CurrencyData> getAllCurrencies(){
        List<CurrencyData> result = new ArrayList<>();
        for (Currency currency : currenciesRepository.getAll()){
            result.add(new CurrencyData(currency.getCurrency(), currency.getIcon()));
        }
        return result;
    }

    String completeLikeString(String input){
        String result = "%";
        for (Integer i = 0; i < input.length(); i++){
            result+=input.charAt(i) + "%";
        }
        return result;
    }

    <T> void mergeLists(List<T> first, List<T> second){
        for (T element : second){
            first.add(element);
        }
    }

}
