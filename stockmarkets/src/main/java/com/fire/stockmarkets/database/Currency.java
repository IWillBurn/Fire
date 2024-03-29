package com.fire.stockmarkets.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "icon")
    private String icon;

    public Currency(String _currency, String _icon){
        currency = _currency;
        icon = _icon;
    }

    public Currency(){
        currency = "";
        icon = "";
    }
}