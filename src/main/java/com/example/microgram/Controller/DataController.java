package com.example.microgram.Controller;

import com.example.microgram.Service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DataController {

    private final DataService service;

    @GetMapping("/")  // генератор данных
    public ResponseEntity<List<String>> insertData() {
        return new ResponseEntity<>(service.insertTestData(), HttpStatus.OK);
    }

}
