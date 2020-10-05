package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TransmissionTest {

    @BeforeEach
    void setUp () {
       val loginPage = open("http://localhost:9999", LoginPage.class);
       val verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
       val verificationCode = DataHelper.getVerificationCodeFor();
       verificationPage.validVerify(verificationCode);
    }

    @Test
    @Order(1)
    void shouldTransmitFromFirstToSecond() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val currentBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), amount);

        val balanceOfFirstCardAfterTransmit = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmit = dashboardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDecrease(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceRise(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmit);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmit);
    }

    @Test
    @Order(2)
    void shouldTransmitFromSecondToFirst() {
        val dashboardPage = new DashboardPage();
        val amount = 500;
        val currentBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashboardPage.transmissionToFirstCard();
        transmissionPage.transmission(getSecondCardNumber(), amount);

        val balanceOfFirstCardAfterTransmit = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmit = dashboardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceRise(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceDecrease(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmit);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmit);
    }

    @Test
    @Order(3)
    void shouldTransmitZeroAmount() {
        val dashboardPage = new DashboardPage();
        val amount = 0;
        val currentBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), amount);

        val balanceOfFirstCardAfterTransmit = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmit = dashboardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDecrease(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceRise(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmit);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmit);
    }
    @Test
    @Order(4)
    void shouldBeErrorIfAmountEmpty() {
        val dashboardPage = new DashboardPage();
        String amount = "";
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), Integer.parseInt(amount));
        transmissionPage.errorTransmittion();
    }

    @Test
    @Order(5)
    void shouldBeErrorIfFromInputCardIsEmpty() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getEmptyCardNumber(), amount);
        transmissionPage.errorTransmittion();
    }

    @Test
    @Order(6)
    void shouldBeErrorIfNotCorrectCardNumber() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getNotCorrectCardNumber(), amount);
        transmissionPage.errorTransmittion();
    }

    @Test
    @Order(7)
    void shouldBeTransmitAmountEqualsCurrentBalance(){
        val dashboardPage = new DashboardPage();
        val amount = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val currentBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transmissionPage = dashboardPage.transmissionToSecondCard();
        transmissionPage.transmission(getFirstCardNumber(), amount);

        val balanceOfFirstCardAfterTransmit = dashboardPage.getCurrentBalanceOfFirstCard();
        val balanceOfSecondCardAfterTransmit = dashboardPage.getCurrentBalanceOfSecondCard();
        val expectedBalanceOfFirstCardAfterTransmit = getExpectedBalanceIfBalanceDecrease(currentBalanceOfFirstCard, amount);
        val expectedBalanceOfSecondCardAfterTransmit = getExpectedBalanceIfBalanceRise(currentBalanceOfSecondCard, amount);
        assertEquals(expectedBalanceOfFirstCardAfterTransmit, balanceOfFirstCardAfterTransmit);
        assertEquals(expectedBalanceOfSecondCardAfterTransmit, balanceOfSecondCardAfterTransmit);
    }

    @Test
    @Order(8)
    void shouldBeErrorIfAmountMoreThanCurrentBalance() {
        val dashboardPage = new DashboardPage();
        val amount = dashboardPage.getCurrentBalanceOfSecondCard() * 2;
        val transmissionPage = dashboardPage.transmissionToFirstCard();
        transmissionPage.transmission(getSecondCardNumber(), amount);
        transmissionPage.errorTransmittion();
    }

}
