package com.fire.stockmarkets.services.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.StockMarketData;
import com.fire.stockmarkets.dto.api.components.DataField;
import com.fire.stockmarkets.dto.api.responses.StockData;
import com.fire.stockmarkets.dto.moex.MOEXCompanyData;
import com.fire.stockmarkets.dto.moex.MOEXSecuritiesHistory;
import com.fire.stockmarkets.services.CurrencyService;
import com.fire.stockmarkets.services.DateService;
import com.fire.stockmarkets.services.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MOEXService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Value("${moex-host}")
    private String host;

    @Value("${moex-protocol}")
    private String protocol;

    public StockData getStockDailyData(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        MOEXCompanyData infoData = getCompanyAndBoardData(symbol, timeoutInSeconds);
        String primaryBoard = findValueByTag(infoData.getBoards().getData(), 14, "1", 1);
        StockMarketData stockMarketData = StockMarketService.getStockMarketData("RUS", "MOEX");
        CompanyData companyData = getCompanyData(infoData);
        CurrencyData currencyData = CurrencyService.getRubleData();
        List<DataField> dataFieldList = getStockHistory(symbol, primaryBoard, timeoutInSeconds);
        return new StockData(stockMarketData, companyData, currencyData, dataFieldList);
    }

    public MOEXCompanyData getCompanyAndBoardData(String symbol, Long timeoutInSeconds) throws JsonProcessingException{
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String url = "https://" + host + "/" + protocol;
        String request = url + "/securities/" + symbol + ".json?lang=en";
        MOEXCompanyData response = restTemplate.getForObject(request, MOEXCompanyData.class);
        return response;
    }

    public CompanyData getCompanyData(MOEXCompanyData response) throws JsonProcessingException {
        String symbol = findValueByTag(response.getDescription().getData(), 0, "SECID", 2);
        String name = findValueByTag(response.getDescription().getData(), 0, "NAME", 2);
        CompanyData companyData = new CompanyData(symbol, name, "");
        return companyData;
    }

    public List<DataField> getStockHistory(String symbol, String board, Long timeoutInSeconds) throws JsonProcessingException {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String url = "https://" + host + "/" + protocol;

        String request = url + "/history/engines/stock/markets/shares/boards/"+ board + "/securities/" + symbol + ".json?from=" + DateService.currentDateWithDelta(3);
        MOEXSecuritiesHistory response = restTemplate.getForObject(request, MOEXSecuritiesHistory.class);

        List<DataField> dataFieldList = new ArrayList<>();

        for (List<String> field: response.getHistory().getData()){
            dataFieldList.add(new DataField(Timestamp.valueOf(field.get(1)).getTime(), Float.valueOf(field.get(6)), Float.valueOf(field.get(7)), Float.valueOf(field.get(8)), Float.valueOf(field.get(10))));
        }
        return dataFieldList;
    }

    public String findValueByTag(List<List<String>> data, Integer tagPosition, String tag, Integer valuePosition){
        for (List<String> field: data){
            if (field.get(tagPosition).equals(tag)){
                return field.get(valuePosition);
            }
        }
        return "";
    }
}
