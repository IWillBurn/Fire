package com.fire.stockmarkets.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "markets")
public class Market {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_market")
    private String stockMarket;

    @Column(name = "country")
    private String country;

    @ManyToOne(optional = false)
    private Currency currency;

    @ManyToMany(mappedBy = "markets")
    private List<Company> companies;
    public Market(String _stockMarket, String _country){
        stockMarket = _stockMarket;
        country = _country;
        companies = new ArrayList<>();
    }
    public Market(){
        companies = new ArrayList<>();
    }
}