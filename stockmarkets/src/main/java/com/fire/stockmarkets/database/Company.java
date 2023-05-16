package com.fire.stockmarkets.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
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

    public Company(String _symbol, String _name, String _description) {
        symbol = _symbol;
        name = _name;
        description = _description;
    }
}
