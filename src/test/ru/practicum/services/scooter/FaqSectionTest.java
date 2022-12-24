package ru.practicum.services.scooter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class FaqSectionTest {

    WebDriver driver = Config.webDriver();
    ScooterMainPage scooterMainPage = new ScooterMainPage(driver);
    FaqSection faqSection = new FaqSection(driver);

    @Before
    public void before() {
        driver.get(Config.SCOOTER_MAIN_PAGE);
        faqSection.scrollToFaqSection();
        scooterMainPage.acceptCookiesIfNotificationPresent();
    }

    @After
    public void after() {
        driver.quit();
    }

    @Test
    public void price() {
        faqSection.verifyPriceAnswerIsInvisible();
        faqSection.clickPriceQuestion();
        faqSection.verifyPriceAnswerIsVisible();
    }

    @Test
    public void orderToday() {
        faqSection.verifyOrderTodayAnswerIsInvisible();
        faqSection.clickOrderTodayQuestion();
        faqSection.verifyOrderTodayAnswerIsVisible();
    }
}
