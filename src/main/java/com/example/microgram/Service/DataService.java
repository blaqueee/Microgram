package com.example.microgram.Service;

import com.example.microgram.DataGenerator.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {
    private final Generator generator;

    public List<String> insertTestData() {
        return generator.insertTestData();
    }
}
