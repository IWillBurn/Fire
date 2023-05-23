package com.fire.stockmarkets.services;

import com.fire.stockmarkets.database.Company;
import com.fire.stockmarkets.database.Source;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExchangesService {

    public static Boolean haveSource(List<Source> sources, String sourceName){
        for (Source source : sources){
            if (sourceName.equals(source.getSource())){
                return true;
            }
        }
        return false;
    }
}
