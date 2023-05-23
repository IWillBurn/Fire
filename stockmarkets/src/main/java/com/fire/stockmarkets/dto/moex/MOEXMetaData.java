package com.fire.stockmarkets.dto.moex;

import lombok.Data;

@Data
public class MOEXMetaData {
    private String type;
    private Long bytes;
    private Long maxSize;

    MOEXMetaData(String _type){
        type = _type;
    }
    MOEXMetaData(Long _bytes){
        bytes = _bytes;
    }
    MOEXMetaData(String _type, Long _bytes, Long _maxSize){
        type = _type;
        bytes = _bytes;
        maxSize = _maxSize;
    }
}
