package com.example.microgram.Utility.DataGenerator;

public enum UserEnum {
    FIRST("blaque", "Kairat", "co453289@gmail.com", "12345678", true),
    SECOND("regina", "Aikosha", "reginadellaluna@gmail.com", "princessofthemoon", true),
    THIRD("carryall", "Andrey", "andrey@gmail.com", "iloveyou", true),
    FOURTH("someone", "Ivan", "vanyusha@mail.ru", "12345", true),
    FIFTH("kitty", "Lara", "larisson@gmail.com", "kitty_girl", true);

    private final String username;
    private final String name;
    private final String email;
    private final String password;
    private final boolean enabled;

    UserEnum(String username, String name, String email, String password, boolean enabled) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }
}
