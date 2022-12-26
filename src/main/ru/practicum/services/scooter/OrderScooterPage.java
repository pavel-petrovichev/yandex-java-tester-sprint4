package ru.practicum.services.scooter;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static ru.practicum.services.scooter.Config.DEFAULT_WAIT_TIME_SECONDS;

@RequiredArgsConstructor
public class OrderScooterPage {

    private final WebDriver driver;
    private final JavascriptExecutor js;

    public OrderScooterPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) this.driver;
    }

    // locators for order buttons
    private final By orderButtonTop = By.xpath(".//div[starts-with(@class, 'Header_Nav')]/button[text()='Заказать']");
    private final By orderButtonBottom = By.xpath(".//div[starts-with(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    // locators for the first form of the scooter order
    private final By firstNameLocator = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameLocator = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressLocator = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By subwayStationInputLocator = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By subwayStationList = By.xpath(".//ul[@class='select-search__options']/li");
    private final By phoneLocator = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By firstFormOrderNextButton = By.xpath(".//button[text()='Далее']");

    // locators for the second form of the scooter order
    private final By deliveryDate = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final String datePickerTemplate = ".//div[@class='react-datepicker__week']/div[text()=%s]";
    private final By rentDurationLocator = By.xpath(".//div[text()='* Срок аренды']");
    private final By dropdownMenuOptions = By.xpath(".//div[@class='Dropdown-menu']/div[@class='Dropdown-option']");
    private final By colorsOptions = By.xpath(".//div[text()='Цвет самоката']/../label");
    private final By commentForCourier = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By secondFormOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[text()='Заказать']");
    private final By secondFormConfirmOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[text()='Да']");

    // order info
    private final By orderProcessed = By.xpath(".//div[contains(text(), 'Заказ оформлен')]");
    private final By showOrderDetailsButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button[text()='Посмотреть статус']");
    private final By cancelOrderButton = By.xpath(".//button[text()='Отменить заказ']");


    public void clickOrderButtonTop() {
        clickElement(orderButtonTop);
    }

    public void waitForBottomOrderButtonToBeClickable() {
        waitForButtonToBeClickable(orderButtonBottom);
    }

    public void scrollToOrderButtonBottom() {
        WebElement element = driver
                .findElement(orderButtonBottom);
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickOrderButtonBottom() {
        clickElement(orderButtonBottom);
    }

    // actions for the first form of the scooter order
    public void waitForFirstForm() {
        waitForButtonToBeClickable(firstFormOrderNextButton);
    }

    public void fillInFirstName(String firstName) {
        fillInElement(firstNameLocator, firstName);
    }

    public void fillInLastName(String lastName) {
        fillInElement(lastNameLocator, lastName);
    }

    public void fillInAddress(String address) {
        fillInElement(addressLocator, address);
    }

    public String pickRandomStation() {
        // open stations list
        clickElement(subwayStationInputLocator);

        // select random station
        List<WebElement> stationsElements = driver.findElements(subwayStationList);
        String station = stationsElements
                .get(ThreadLocalRandom
                        .current()
                        .nextInt(stationsElements.size()))
                .getText();

        // close stations list
        clickElement(subwayStationInputLocator);

        return station;
    }

    public void selectSubwayStation(String station) {
        // open stations list
        clickElement(subwayStationInputLocator);

        // select station
        driver
                .findElements(subwayStationList)
                .stream()
                .filter(e -> Objects.equals(e.getText(), station))
                .findFirst()
                .orElseThrow()
                .click();
    }

    public void fillInPhone(String phone) {
        fillInElement(phoneLocator, phone);
    }

    public void clickOrderNextButton() {
        clickElement(firstFormOrderNextButton);
    }

    // actions for the second form of the scooter order
    public void waitForSecondForm() {
        waitForButtonToBeClickable(secondFormOrderButton);
    }

    public void waitForSecondFormConfirmationButton() {
        waitForButtonToBeClickable(secondFormConfirmOrderButton);
    }

    public void selectDeliveryDate(LocalDateTime localDateTime) {
        // todo works for current month only
        driver
                .findElement(deliveryDate)
                .click();
        int dayOfMonth = localDateTime.getDayOfMonth();
        driver
                .findElement(By.xpath(String.format(datePickerTemplate, dayOfMonth)))
                .click();
    }

    public String pickRandomRentDuration() {
        clickElement(rentDurationLocator);

        List<WebElement> rentDurationOptions = driver
                .findElements(dropdownMenuOptions);
        String duration = rentDurationOptions
                .get(ThreadLocalRandom
                        .current()
                        .nextInt(rentDurationOptions.size()))
                .getText();

        clickElement(rentDurationLocator);

        return duration;
    }

    public void selectRentDuration(String duration) {
        clickElement(rentDurationLocator);
        driver
                .findElements(dropdownMenuOptions)
                .stream()
                .filter(e -> Objects.equals(e.getText(), duration))
                .findFirst()
                .orElseThrow()
                .click();
    }

    public String pickRandomScooterColor() {
        List<WebElement> colors = driver
                .findElements(colorsOptions);
        return colors
                .get(ThreadLocalRandom
                        .current()
                        .nextInt(colors.size()))
                .getText();
    }

    public void selectScooterColor(String color) {
        driver
                .findElements(colorsOptions)
                .stream()
                .filter(e -> Objects.equals(e.getText(), color))
                .findFirst()
                .orElseThrow()
                .click();
    }

    public void writeComment(String comment) {
        fillInElement(commentForCourier, comment);
    }

    public void clickOrderButton() {
        clickElement(secondFormOrderButton);
    }

    public void clickConfirmOrderButton() {
        clickElement(secondFormConfirmOrderButton);
    }

    // order details actions
    public void clickOrderDetailsButton() {
        clickElement(showOrderDetailsButton);
    }

    public void waitForOrderProcessedPage() {
        WebDriverWait webDriverWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_WAIT_TIME_SECONDS));
        WebElement orderProcessedElement = driver
                .findElement(orderProcessed);
        webDriverWait
                .until(ExpectedConditions.visibilityOf(orderProcessedElement));
    }

    public void waitForCancelOrderButton() {
        waitForButtonToBeClickable(cancelOrderButton);
    }

    private void waitForButtonToBeClickable(By locator) {
        new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_WAIT_TIME_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void fillInElement(By locator, String text) {
        driver
                .findElement(locator)
                .sendKeys(text);
    }

    private void clickElement(By locator) {
        driver
                .findElement(locator)
                .click();
    }
}
