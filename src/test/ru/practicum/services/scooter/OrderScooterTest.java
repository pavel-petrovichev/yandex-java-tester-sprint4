package ru.practicum.services.scooter;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.util.Locale;

public class OrderScooterTest {

    WebDriver driver = Config.webDriver();
    ScooterMainPage scooterMainPage = new ScooterMainPage(driver);
    OrderScooterPage orderScooterPage = new OrderScooterPage(driver);
    Faker faker = new Faker(new Locale("ru"));

    @Before
    public void before() {
        driver.get(Config.SCOOTER_MAIN_PAGE);
        scooterMainPage.acceptCookiesIfNotificationPresent();
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void orderScooter__topButton() {
        orderScooterPage.clickOrderButtonTop();
        orderScooterPage.waitForFirstForm();

        orderScooterPage.fillInFirstName(faker.name().firstName());
        orderScooterPage.fillInLastName(faker.name().lastName());
        orderScooterPage.fillInAddress(faker.address().streetAddress());
        orderScooterPage.selectSubwayStation(orderScooterPage.pickRandomStation());
        orderScooterPage.fillInPhone(generatePhoneNumber());
        orderScooterPage.clickOrderNextButton();
        orderScooterPage.waitForSecondForm();

        orderScooterPage.selectDeliveryDate(LocalDateTime.now());
        orderScooterPage.selectRentDuration(orderScooterPage.pickRandomRentDuration());
        orderScooterPage.selectScooterColor(orderScooterPage.pickRandomScooterColor());
        orderScooterPage.writeComment(faker.hitchhikersGuideToTheGalaxy().marvinQuote());
        orderScooterPage.clickOrderButton();
        orderScooterPage.waitForSecondFormConfirmationButton();
        orderScooterPage.clickConfirmOrderButton();
        orderScooterPage.waitForOrderProcessedPage();

        orderScooterPage.clickOrderDetailsButton();
        orderScooterPage.waitForCancelOrderButton();
    }

    @Test
    public void orderScooter__bottomButton() {
        orderScooterPage.waitForBottomOrderButtonToBeClickable();
        orderScooterPage.scrollToOrderButtonBottom();
        orderScooterPage.clickOrderButtonBottom();

        orderScooterPage.fillInFirstName(faker.name().firstName());
        orderScooterPage.fillInLastName(faker.name().lastName());
        orderScooterPage.fillInAddress(faker.address().streetAddress());
        orderScooterPage.selectSubwayStation(orderScooterPage.pickRandomStation());
        orderScooterPage.fillInPhone(generatePhoneNumber());
        orderScooterPage.clickOrderNextButton();
        orderScooterPage.waitForSecondForm();

        orderScooterPage.selectDeliveryDate(LocalDateTime.now());
        orderScooterPage.selectRentDuration(orderScooterPage.pickRandomRentDuration());
        orderScooterPage.selectScooterColor(orderScooterPage.pickRandomScooterColor());
        orderScooterPage.writeComment(faker.hitchhikersGuideToTheGalaxy().marvinQuote());
        orderScooterPage.clickOrderButton();
        orderScooterPage.waitForSecondFormConfirmationButton();
        orderScooterPage.clickConfirmOrderButton();
        orderScooterPage.waitForOrderProcessedPage();

        orderScooterPage.clickOrderDetailsButton();
        orderScooterPage.waitForCancelOrderButton();
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
}
