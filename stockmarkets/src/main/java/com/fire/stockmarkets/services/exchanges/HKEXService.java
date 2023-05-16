package com.fire.stockmarkets.services.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.stockmarkets.dto.api.components.CompanyData;
import com.fire.stockmarkets.dto.api.components.CurrencyData;
import com.fire.stockmarkets.dto.api.components.StockMarketData;
import com.fire.stockmarkets.dto.api.components.ValueData;
import com.fire.stockmarkets.dto.api.responses.StockDailyData;
import com.fire.stockmarkets.dto.hkex.HKEXMetaData;
import com.fire.stockmarkets.dto.hkex.HKEXMetaInfo;
import com.fire.stockmarkets.dto.hkex.HKEXSecuritiesResult;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HKEXService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Value("${hkex-host}")
    private String host;

    @Value("${hkex-api}")
    private String api;

    @Value("${hkex-dataset}")
    private String dataset;

    @Value("${hkex-api-key}")
    private String apiKey;

    public StockDailyData getStockDailyData(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        StockMarketData stockMarketData = StockMarketService.getStockMarketData("CHN", "HKEX");
        CompanyData companyData = getCompanyData(symbol, timeoutInSeconds);
        CurrencyData currencyData = CurrencyService.getHKDollarData();
        List<ValueData> valueDataList = getStockHistory(symbol, timeoutInSeconds);
        return new StockDailyData(stockMarketData, companyData, currencyData, valueDataList);
    }

    public List<ValueData> getStockHistory(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String apiUrl = "https://" + host + "/" + api + "/" + dataset;
        String dataRequest = apiUrl + "/" + symbol + "/" + "data.json" + "?api_key=" + apiKey + "&start_date=" + DateService.currentDate(3);;
        HKEXSecuritiesResult dataResponse = restTemplate.getForObject(dataRequest, HKEXSecuritiesResult.class);

        List<ValueData> data = new ArrayList<>();
        for (List<String> field: dataResponse.getDatasetData().getData()){
            data.add(new ValueData(field.get(0), field.get(9), field.get(8), field.get(7), field.get(1)));
        }
        Collections.reverse(data);
        return data;
    }
    public CompanyData getCompanyData(String symbol, Long timeoutInSeconds) throws JsonProcessingException {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String apiUrl = "https://" + host + "/" + api + "/" + dataset;

        String metaDataRequest = apiUrl + "/" + symbol + "/" + "metadata.json" + "?api_key=" + apiKey;
        HKEXMetaInfo metaDataResponseRaw = restTemplate.getForObject(metaDataRequest, HKEXMetaInfo.class);

        HKEXMetaData metaDataResponse = metaDataResponseRaw.getDataset();

        String name = metaDataResponse.getName();
        name = name.substring(0, name.length() - 9);

        return new CompanyData(metaDataResponse.getDatasetCode(), name, "");
    }
}
