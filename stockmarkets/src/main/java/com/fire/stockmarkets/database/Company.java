package com.fire.stockmarkets.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "companies_sources",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = { @JoinColumn (name = "source_id")}
    )
    private List<Source> sources = new ArrayList<>();;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "companies_markets",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = { @JoinColumn (name = "market_id")}
    )
    private List<Market> markets = new ArrayList<>();;

    public void addSource(Source source){
        sources.add(source);
        source.getCompanies().add(this);
    }
    public Boolean containSource(Source source){
        return sources.contains(source);
    }
    public void removeSource(Source source){
        sources.remove(source);
        source.getCompanies().remove(this);
    }
    public void addMarket(Market market){
        markets.add(market);
        market.getCompanies().add(this);
    }
    public Boolean containMarket(Market market){
        return markets.contains(market);
    }
    public void removeMarket(Market market){
        markets.remove(market);
        market.getCompanies().remove(this);
    }

    public Company(String _symbol, String _name, String _description, List<Source> _sources, List<Market> _markets) {
        symbol = _symbol;
        name = _name;
        description = _description;
        sources = _sources;
        markets = _markets;
    }
    public Company(){
        symbol = "";
        name = "";
        description = "";
        sources = new ArrayList<>();
        markets = new ArrayList<>();
    }
}
