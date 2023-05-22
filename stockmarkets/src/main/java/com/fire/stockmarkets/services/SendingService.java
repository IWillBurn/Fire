package com.fire.stockmarkets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SendingService {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public <R> R sendRequest(String url, Long timeoutInSeconds, Class responseType) {
        RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(timeoutInSeconds)).setReadTimeout(Duration.ofSeconds(timeoutInSeconds)).build();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        System.out.println("sending to " + url);
        ResponseEntity<R> result = (ResponseEntity<R>) restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        System.out.println("have result");
        if (result.getStatusCode().is2xxSuccessful()){
            return result.getBody();
        }
        return null;
    }

    public String createUrl(String main, List<String> ways, Map<String, String> parameters){
        String url = main;
        for (String way : ways){
            url += "/"+ way;
        }
        Set<String> keys = parameters.keySet();
        if (keys.size() > 0) {
            url += "?";
            for (String key : keys){
                url += key + "=" + parameters.get(key) + "&";
            }
            url = url.substring(0, url.length()-1);
        }
        return "https://"+url;
    }
}
