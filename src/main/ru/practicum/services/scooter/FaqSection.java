package ru.practicum.services.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class FaqSection {

    private final WebDriver driver;
    private final JavascriptExecutor js;

    private final By faqHeader = By.className("Home_FourPart__1uthg");
    private final By priceQuestion = By.xpath(".//div[contains(text(), 'Сколько это стоит?')]");
    private final By priceAnswer = By
            .xpath(".//p[contains(text(), " +
                    "'Сутки — 400 рублей. Оплата курьеру — наличными или картой.')]");
    private final By orderTodayQuestion = By.xpath(".//div[contains(text(), 'Можно ли заказать самокат прямо на сегодня?')]");
    private final By orderTodayAnswer = By
            .xpath(".//p[contains(text(), " +
                    "'Только начиная с завтрашнего дня. Но скоро станем расторопнее.')]");


    public FaqSection(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) this.driver;
    }

    public void scrollToFaqSection() {
        WebElement element = driver
                .findElement(faqHeader);
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickPriceQuestion() {
        clickElement(priceQuestion);
    }

    public void verifyPriceAnswerIsInvisible() {
        assertThat(isElementDisplayed(priceAnswer))
                .withFailMessage("Price answer is displayed")
                .isFalse();
    }

    public void verifyPriceAnswerIsVisible() {
        assertThat(isElementDisplayed(priceAnswer))
                .withFailMessage("Price answer is not displayed")
                .isTrue();
    }

    public void clickOrderTodayQuestion() {
        clickElement(orderTodayQuestion);
    }

    public void verifyOrderTodayAnswerIsInvisible() {
        assertThat(isElementDisplayed(orderTodayAnswer))
                .withFailMessage("Today order answer is displayed")
                .isFalse();
    }

    public void verifyOrderTodayAnswerIsVisible() {
        assertThat(isElementDisplayed(orderTodayAnswer))
                .withFailMessage("Today order answer is displayed")
                .isTrue();
    }

    private void clickElement(By locator) {
        driver
                .findElement(locator)
                .click();
    }

    private boolean isElementDisplayed(By locator) {
        return driver
                .findElement(locator)
                .isDisplayed();
    }
}
