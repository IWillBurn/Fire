package com.fire.stockmarkets.services.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.StockMarketData;
import com.fire.stockmarkets.dto.api.components.ValueData;
import com.fire.stockmarkets.dto.api.responses.StockDailyData;
import com.fire.stockmarkets.dto.us.USCompanyData;
import com.fire.stockmarkets.dto.us.USData;
import com.fire.stockmarkets.dto.us.USSecuritiesRawResult;
import com.fire.stockmarkets.dto.us.USSecuritiesResult;
import com.fire.stockmarkets.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class USService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Value("${us-host}")
    private String host;

    @Value("${us-api}")
    private String api;

    @Value("${us-api-key}")
    private String apiKey;

    public StockDailyData getStockDailyData(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        List<ValueData> data = getStockHistory(symbol, timeoutInSeconds);
        USCompanyData usCompanyData = getCompanyAndStockMarketData(symbol, timeoutInSeconds);
        CompanyData companyData = getCompanyData(usCompanyData);
        StockMarketData stockMarketData = getStockMarketData(usCompanyData);
        CurrencyData currencyData = CurrencyService.getDollarData();
        return new StockDailyData(stockMarketData, companyData, currencyData, data);
    }

    public List<ValueData> getStockHistory(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String function = "?function=TIME_SERIES_DAILY_ADJUSTED";
        String parameters = "&symbol=" + symbol + "&apikey=" + apiKey;
        String apiUrl = "https://" + host + "/" + api;

        String dataRequest = apiUrl + function + parameters;
        USSecuritiesRawResult dataResponse = restTemplate.getForObject(dataRequest, USSecuritiesRawResult.class);
        USSecuritiesResult result = dataProcessing(dataResponse);

        List<ValueData> data = new ArrayList<>();

        for (USData field: result.getData()){
            data.add(new ValueData(field.getDate(), field.getOpen(), field.getLow(), field.getHigh(), field.getClose()));
        }
        Collections.reverse(data);
        return data;
    }

    public USCompanyData getCompanyAndStockMarketData(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String function = "?function=OVERVIEW";
        String parameters = "&symbol=" + symbol + "&apikey=" + apiKey;
        String apiUrl = "https://" + host + "/" + api;

        String dataRequest = apiUrl + function + parameters;
        USCompanyData dataResponse = restTemplate.getForObject(dataRequest, USCompanyData.class);
        return dataResponse;
    }

    public CompanyData getCompanyData(USCompanyData response) throws JsonProcessingException {
        return new CompanyData(response.getSymbol(), response.getName(), response.getDescription());
    }

    public StockMarketData getStockMarketData(USCompanyData response) throws JsonProcessingException {
        return new StockMarketData(response.getCountry(), response.getExchange());
    }

    USSecuritiesResult dataProcessing(USSecuritiesRawResult rawResult){
        USSecuritiesResult result = new USSecuritiesResult();
        List<USData> data = new ArrayList<>();
        Set<String> keys = rawResult.getRawData().keySet();
        for (String key : keys){
            rawResult.getRawData().get(key).setDate(key);
            data.add(rawResult.getRawData().get(key));
        }
        result.setData(data);
        return result;
    }
}
