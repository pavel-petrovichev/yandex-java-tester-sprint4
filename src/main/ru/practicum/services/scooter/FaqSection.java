package ru.practicum.services.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.services.scooter.Config.DEFAULT_WAIT_TIME_SECONDS;

public class FaqSection {

    private final WebDriver driver;
    private final JavascriptExecutor js;

    private final By faqHeader = By.xpath(".//div[text()='Вопросы о важном']");

    public FaqSection(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) this.driver;
    }

    public void scrollToFaqSection() {
        js.executeScript(
                "arguments[0].scrollIntoView();",
                driver.findElement(faqHeader));
    }

    public void clickQuestion(String question) {
        By locator = By.xpath(String.format(".//div[contains(text(), '%s')]", question));
        clickElement(locator);
    }

    public void verifyAnswerIsInvisible(String answer) {
        By locator = By.xpath(String.format(".//p[contains(text(), '%s')]", answer));
        assertThat(isElementDisplayed(locator))
                .withFailMessage("Answer is displayed: %s", answer)
                .isFalse();
    }

    public void waitForAnswerToBeVisible(String answer) {
        By locator = By.xpath(String.format(".//p[contains(text(), '%s')]", answer));
        WebDriverWait webDriverWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_WAIT_TIME_SECONDS));
        WebElement answerElement = driver
                .findElement(locator);
        webDriverWait
                .until(ExpectedConditions.visibilityOf(answerElement));
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
