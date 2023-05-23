package com.fire.stockmarkets.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "data")
public class Data {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "date")
    private String date;

    @Column(name = "open_value")
    private Float openValue;

    @Column(name = "min_value")
    private Float minValue;

    @Column(name = "max_value")
    private Float maxValue;

    @Column(name = "close_value")
    private Float closeValue;

    @ManyToOne(optional = false)
    private Company company;

    @ManyToOne(optional = false)
    private Currency currency;

    @ManyToOne(optional = false)
    private Market market;

    @ManyToOne(optional = false)
    private Source source;

    public Data(String _dataType,
                String _date,
                Float _openValue,
                Float _minValue,
                Float _maxValue,
                Float _closeValue,
                Company _company,
                Currency _currency,
                Market _market,
                Source _source){
        dataType = _dataType;
        date = _date;
        openValue = _openValue;
        minValue = _minValue;
        maxValue = _maxValue;
        closeValue = _closeValue;
        company = _company;
        currency = _currency;
        market = _market;
        source = _source;
    }

}