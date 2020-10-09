package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class TransmissionPage {
    private final SelenideElement amountInput = $("[data-test-id = amount] input");
    private final SelenideElement fromInput = $("[data-test-id = from] input");
    private final SelenideElement transmissionButton = $("[data-test-id = action-transfer]");
    private final SelenideElement error = $("[data-test-id = error-notification]");

    public TransmissionPage() {
        SelenideElement heading = $(byText("Пополнение карты"));
        heading.shouldBe(visible);
    }

    public void transmission(DataHelper.TransmissionInfo transmissionInfo, String amount) {
        amountInput.setValue(amount);
        fromInput.setValue(transmissionInfo.getCard());
        transmissionButton.click();
        new DashboardPage();
    }

    public void errorTransmittion() {
        error.shouldBe(visible);
    }
}
