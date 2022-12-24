package ru.practicum.services.scooter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class OrderScooterTest {

    WebDriver driver = Config.webDriver();
    ScooterMainPage scooterMainPage = new ScooterMainPage(driver);
    OrderScooterPage orderScooterPage = new OrderScooterPage(driver);

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

        orderScooterPage.fillInFirstForm();
        orderScooterPage.clickOrderNextButton();
        orderScooterPage.waitForSecondForm();

        orderScooterPage.fillInSecondForm();
        orderScooterPage.clickOrderButton();
        orderScooterPage.waitForSecondFormConfirmationButton();
        orderScooterPage.clickConfirmOrderButton();
        orderScooterPage.waitForOrderProcessedPage();

        orderScooterPage.clickOrderDetailsButton();
        orderScooterPage.waitForCancelOrderButton();
    }

    @Test
    public void orderScooter__bottomButton() {
        orderScooterPage.scrollToOrderButtonBottom();
        orderScooterPage.clickOrderButtonBottom();

        orderScooterPage.fillInFirstForm();
        orderScooterPage.clickOrderNextButton();
        orderScooterPage.waitForSecondForm();

        orderScooterPage.fillInSecondForm();
        orderScooterPage.clickOrderButton();
        orderScooterPage.waitForSecondFormConfirmationButton();
        orderScooterPage.clickConfirmOrderButton();
        orderScooterPage.waitForOrderProcessedPage();

        orderScooterPage.clickOrderDetailsButton();
        orderScooterPage.waitForCancelOrderButton();
    }
}
