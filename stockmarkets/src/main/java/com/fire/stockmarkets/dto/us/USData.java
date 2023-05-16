package com.fire.stockmarkets.dto.us;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class USData {
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjustedClose;
    private String volume;
    private String dividendAmount;
    private String splitCoefficient;

    @JsonCreator
    public USData(@JsonProperty("1. open") String _open,
                  @JsonProperty("2. high") String _high,
                  @JsonProperty("3. low") String _low,
                  @JsonProperty("4. close") String _close,
                  @JsonProperty("5. adjusted close") String _adjustedClose,
                  @JsonProperty("6. volume") String _volume,
                  @JsonProperty("7. dividend amount") String _dividendAmount,
                  @JsonProperty("8. split coefficient") String _splitCoefficient){
        open = _open;
        high = _high;
        low = _low;
        close = _close;
        adjustedClose = _adjustedClose;
        volume = _volume;
        dividendAmount = _dividendAmount;
        splitCoefficient = _splitCoefficient;
    }

}
