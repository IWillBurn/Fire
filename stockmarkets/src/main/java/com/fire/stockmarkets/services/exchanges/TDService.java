package com.fire.stockmarkets.services.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.stockmarkets.database.*;
import com.fire.stockmarkets.dto.api.components.*;
import com.fire.stockmarkets.dto.api.responses.StockData;
import com.fire.stockmarkets.dto.dt.*;
import com.fire.stockmarkets.services.DateService;
import com.fire.stockmarkets.services.ExchangesService;
import com.fire.stockmarkets.services.SendingService;
import com.fire.stockmarkets.services.TokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TDService implements StockMarketDataService{

    @Autowired
    SendingService sendingService;
    @Autowired
    CompaniesRepository companiesRepository;
    @Autowired
    CurrenciesRepository currenciesRepository;
    @Autowired
    SourcesRepository sourcesRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    MarketsRepository marketsRepository;

    @Autowired
    TokensService tokensService;

    String sourceName = "TD";
    Source source;
    @Value("${td-host}")
    private String host;

    @Value("${td-api-key}")
    private String apiKey;

    public void initService(){
        if (sourcesRepository.findBySource(sourceName) == null) {
            Source mySource = new Source(sourceName);
            sourcesRepository.save(mySource);
        }
        source = sourcesRepository.findBySource(sourceName);
    }

    public void addAllMarketsToDataBase(){
        List<String> ways = new ArrayList<>();
        ways.add("stocks");
        Map<String, String> parameters = new HashMap<>();

        String url = sendingService.createUrl(host, ways, parameters);
        try {
            TDStockSearchResult stocks = sendingService.sendRequest(url,100000L, TDStockSearchResult.class);
            for (TDFindMetaData stock : stocks.getData()){
                if (marketsRepository.findByName(stock.getExchange()) == null){
                    Market newMarket = new Market(stock.getExchange(), stock.getCountry());
                    marketsRepository.save(newMarket);
                }
                if (currenciesRepository.findByName(stock.getCurrency()) == null){
                    Currency newCurrency = new Currency(stock.getCurrency(), stock.getCurrency());
                    currenciesRepository.save(newCurrency);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMarketsCurrencies(){
        List<String> ways = new ArrayList<>();
        ways.add("stocks");
        Map<String, String> parameters = new HashMap<>();

        String url = sendingService.createUrl(host, ways, parameters);
        try {
            TDStockSearchResult stocks = sendingService.sendRequest(url,100000L, TDStockSearchResult.class);
            for (TDFindMetaData stock : stocks.getData()){
                Currency currency = currenciesRepository.findByName(stock.getCurrency());
                Market market = marketsRepository.findByName(stock.getExchange());
                if (market.getCurrency() == null){
                    market.setCurrency(currency);
                    marketsRepository.save(market);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addAllStocksToDataBase(){
        List<String> ways = new ArrayList<>();
        String lastSymbol = "";
        Company lastCompany = null;
        ways.add("stocks");
        Map<String, String> parameters = new HashMap<>();
        Source source = sourcesRepository.findBySource(sourceName);
        String url = sendingService.createUrl(host, ways, parameters);
        try {
            TDStockSearchResult stocks = sendingService.sendRequest(url,100000L, TDStockSearchResult.class);
            for (TDFindMetaData stock : stocks.getData()) {
                Market market = marketsRepository.findByName(stock.getExchange());
                if (stock.getSymbol().equals(lastSymbol)){
                    if (!lastCompany.containSource(source)){
                        lastCompany.addSource(source);
                    }
                    if (!lastCompany.containMarket(market)){
                        lastCompany.addMarket(market);
                    }
                }
                else{
                    if (lastCompany != null) {
                        companiesRepository.save(lastCompany);
                    }
                    lastSymbol = stock.getSymbol();
                    List<Source> sources = new ArrayList<>();
                    sources.add(source);
                    List<Market> markets = new ArrayList<>();
                    markets.add(market);
                    Company newCompany = new Company(stock.getSymbol(), stock.getName(), "", sources, markets);
                    lastCompany = newCompany;
                }
            }
            if (lastCompany != null) {
                companiesRepository.save(lastCompany);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StockData addIntervalData(String symbol, String marketName, String dataType){
        if (haveStock(symbol)){
            String lastField = dataRepository.findLastBySymbolAndMarketInType(symbol, marketName, dataType, source.getId());
            if (lastField == null) {
                lastField = "2023-01-01";
            }
            String current = DateService.currentDate();
            List<String> ways = new ArrayList<>();
            ways.add("time_series");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("symbol", symbol);
            parameters.put("interval", dataType);
            parameters.put("exchange", marketName);
            parameters.put("apikey", tokensService.getKey());
            parameters.put("start_date", lastField);
            parameters.put("end_date", current);
            parameters.put("timezone", "UTC");

            String url = sendingService.createUrl(host, ways, parameters);
            try {
                TDTimeSeriesData result = sendingService.sendRequest(url,1000L, TDTimeSeriesData.class);
                Company company = companiesRepository.findBySymbol(result.getMeta().getSymbol());
                Currency currency = currenciesRepository.findByName(result.getMeta().getCurrency());
                Market market = marketsRepository.findByName(result.getMeta().getExchange());
                for (Integer i = 0; i < result.getValues().size() - 1; i++){
                    TDData resultField = result.getValues().get(i);
                    Data newField = new Data(dataType, resultField.getDatetime(), resultField.getOpen(), resultField.getLow(), resultField.getHigh(), resultField.getClose(), company, currency, market, source);
                    dataRepository.save(newField);
                }
                return new StockData(market, company, currency);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public Float getCurrentData(String symbol, Market market) {
        if (haveStock(symbol)) {
            List<String> ways = new ArrayList<>();
            ways.add("price");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("symbol", symbol);
            parameters.put("exchange", market.getStockMarket());
            parameters.put("apikey", tokensService.getKey());
            String url = sendingService.createUrl(host, ways, parameters);

            try {
                TDCurrentValue result = sendingService.sendRequest(url,1000L, TDCurrentValue.class);
                return result.getPrice();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public Float getExchange(Currency from, Currency to){
        List<String> ways = new ArrayList<>();
        ways.add("exchange_rate");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("symbol", from.getCurrency() + "/" + to.getCurrency());
        parameters.put("apikey", tokensService.getKey());
        String url = sendingService.createUrl(host, ways, parameters);

        try {
            TDExchangeValue result = sendingService.sendRequest(url,1000L, TDExchangeValue.class);
            return result.getRate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean haveStock(String symbol) {
        return ExchangesService.haveSource(companiesRepository.findBySymbol(symbol).getSources(), sourceName);
    }

    @Override
    public CompanyData getCompanyData(String symbol) {
        return null;
    }

    @Override
    public List<DataField> getDailyData(String symbol, Long count) {
        return null;
    }

    @Override
    public List<DataField> getHourData(String symbol, Long count) {
        return null;
    }
}
