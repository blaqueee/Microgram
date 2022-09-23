package com.example.microgram.DataGenerator;

public enum UserEnum {
    FIRST("blaque", "Kairat", "co453289@gmail.com", "12345678"),
    SECOND("regina", "Aikosha", "reginadellaluna@gmail.com", "princessofthemoon"),
    THIRD("carryall", "Andrey", "andrey@gmail.com", "iloveyou"),
    FOURTH("someone", "Ivan", "vanyusha@mail.ru", "12345"),
    FIFTH("kitty", "Lara", "larisson@gmail.com", "kitty_girl");

    private final String username;
    private final String name;
    private final String email;
    private final String password;

    UserEnum(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
