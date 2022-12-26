package ru.practicum.services.scooter;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class ScooterMainPage {

    private final WebDriver driver;

    private final By acceptCookiesLocator = By.id("rcc-confirm-button");

    public void acceptCookiesIfNotificationPresent() {
        WebElement acceptCookiesButton;
        try {
            acceptCookiesButton = driver
                    .findElement(acceptCookiesLocator);
        } catch (NoSuchElementException e) {
            // okay, no cookies warning no problem
            return;
        }
        if (acceptCookiesButton.isEnabled()) {
            acceptCookiesButton.click();
        }
    }
}
