package ru.practicum.services.scooter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Config {
    public static final String SCOOTER_MAIN_PAGE =
            "https://qa-scooter.praktikum-services.ru/";
    public static final int DEFAULT_WAIT_TIME_SECONDS = 10;

    public static WebDriver webDriver() {
        String driverProperty = System.getProperty("driver");
        if (driverProperty == null) {
            driverProperty = "chrome";
        }
        switch (driverProperty.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new RuntimeException(String.format(
                        "Unknown driver: %s. Choose 'chrome' or 'firefox",
                        driverProperty));
        }
    }
}
