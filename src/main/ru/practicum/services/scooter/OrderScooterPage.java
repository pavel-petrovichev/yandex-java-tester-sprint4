package ru.practicum.services.scooter;

import com.github.javafaker.Faker;
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
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import static ru.practicum.services.scooter.Config.DEFAULT_WAIT_TIME_SECONDS;

@RequiredArgsConstructor
public class OrderScooterPage {

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final Faker faker = new Faker(new Locale("ru"));

    public OrderScooterPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) this.driver;
    }

    // locators for order buttons
    private final By orderButtonTop = By.xpath(".//div[starts-with(@class, 'Header_Nav')]/button[text()='Заказать']");
    private final By orderButtonBottom = By.xpath(".//div[starts-with(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    // locators for the first form of the scooter order
    private final By firstName = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastName = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By address = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By subwayStationInput = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By subwayStationList = By.xpath(".//ul[@class='select-search__options']/li");
    private final By phone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By firstFormOrderNextButton = By.xpath(".//button[text()='Далее']");

    // locators for the second form of the scooter order
    private final By deliveryDate = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final String datePickerTemplate = ".//div[@class='react-datepicker__week']/div[text()=%s]";
    private final By rentDuration = By.xpath(".//div[text()='* Срок аренды']");
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
    public void fillInFirstForm() {
        fillInFirstName();
        fillInLastName();
        fillInAddress();
        selectSubwayStation();
        fillInPhone();
    }

    public void waitForFirstForm() {
        waitForButtonToBeClickable(firstFormOrderNextButton);
    }

    public void fillInFirstName() {
        fillInElement(firstName, faker.name().firstName());
    }

    public void fillInLastName() {
        fillInElement(lastName, faker.name().lastName());
    }

    public void fillInAddress() {
        fillInElement(address, faker.address().streetAddress());
    }

    public void selectSubwayStation() {
        // open stations list
        clickElement(subwayStationInput);

        // select random station
        List<WebElement> stationsElements = driver.findElements(subwayStationList);
        stationsElements
                .get(ThreadLocalRandom
                        .current()
                        .nextInt(stationsElements.size()))
                .click();
    }

    public void fillInPhone() {
        fillInElement(phone, generatePhoneNumber());
    }

    public void clickOrderNextButton() {
        clickElement(firstFormOrderNextButton);
    }

    // actions for the second form of the scooter order
    public void fillInSecondForm() {
        selectDeliveryDate();
        selectRentDuration();
        selectScooterColor();
        writeComment();
    }

    public void waitForSecondForm() {
        waitForButtonToBeClickable(secondFormOrderButton);
    }

    public void waitForSecondFormConfirmationButton() {
        waitForButtonToBeClickable(secondFormConfirmOrderButton);
    }

    public void selectDeliveryDate() {
        // todo consider selecting tomorrow
        // two options here:
        //  tomorrow is within current month
        //  tomorrow is next month, click 'next month' then
        driver
                .findElement(deliveryDate)
                .click();
        LocalDateTime now = LocalDateTime.now();
        int dayOfMonth = now.getDayOfMonth();
        driver
                .findElement(By.xpath(String.format(datePickerTemplate, dayOfMonth)))
                .click();
    }

    public void selectRentDuration() {
        clickElement(rentDuration);

        List<WebElement> rentDurationOptions = driver
                .findElements(dropdownMenuOptions);
        rentDurationOptions
                .get(ThreadLocalRandom
                        .current()
                        .nextInt(rentDurationOptions.size()))
                .click();
    }

    public void selectScooterColor() {
        List<WebElement> colors = driver
                .findElements(colorsOptions);
        colors
                .get(ThreadLocalRandom
                        .current()
                        .nextInt(colors.size()))
                .click();
    }

    public void writeComment() {
        fillInElement(commentForCourier, faker.hitchhikersGuideToTheGalaxy().marvinQuote());
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

    // dirty hack since Faker cannot format phone numbers
    private String generatePhoneNumber() {
        return faker
                .phoneNumber()
                .phoneNumber()
                .replace("(", "")
                .replace(")", "")
                .replace("-", "");
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
