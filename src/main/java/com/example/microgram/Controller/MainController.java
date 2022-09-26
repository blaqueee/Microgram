package com.example.microgram.Controller;

import com.example.microgram.DTO.UserDto;
import com.example.microgram.Service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final MainService service;

    @PostMapping("/register")
    /* Регистрация отправляется в таком виде
        {
            "username": "blaque",
            "name": "Halo",
            "email": "bla@bla.bla",
            "password": "12345678"
        }
     */
    public ResponseEntity<String> createNewUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(service.createNewUser(userDto), HttpStatus.OK);
    }


    @PostMapping("/login")
    /* Логин отправляется в таком виде
        {
            "username": "blaque",
            "password": "12345678"
        }
     */
    public ResponseEntity<String> loginByUsername(@RequestBody UserDto userDto, HttpServletResponse response) {
        return new ResponseEntity<>(service.loginByUsername(userDto, response), HttpStatus.OK);
    }
}
