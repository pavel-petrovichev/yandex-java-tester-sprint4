package ru.practicum.services.scooter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Config {
    public static final String SCOOTER_MAIN_PAGE =
            "https://qa-scooter.praktikum-services.ru/";

    public static WebDriver webDriver() {
        return new FirefoxDriver();
    }
}
