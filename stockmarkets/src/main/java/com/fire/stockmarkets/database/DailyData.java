package com.fire.stockmarkets.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Entity
@Table(name = "daily_data")
public class DailyData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "open_value")
    private String openValue;

    @Column(name = "min_value")
    private String minValue;

    @Column(name = "max_value")
    private String maxValue;

    @Column(name = "close_value")
    private String closeValue;

    @ManyToOne(optional = false)
    private Company company;

    @ManyToOne(optional = false)
    private Currency currency;

    @ManyToOne(optional = false)
    private Market market;
}