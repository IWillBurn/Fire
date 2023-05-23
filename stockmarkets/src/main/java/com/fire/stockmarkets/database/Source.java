package com.fire.stockmarkets.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
@Table(name = "sources")
public class Source {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "source")
    private String source;

    @ManyToMany(mappedBy = "sources")
    private List<Company> companies;

    public Source(String _source) {
        source = _source;
        companies = new ArrayList<>();
    }

    public Source() {
        companies = new ArrayList<>();
    }
}
