package ru.netology.card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ChangeDateTest {

    @BeforeEach
    void setup() {

        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully schedule and reschedule meeting")
    void putTheDateInThreeDaysThenInTenDays() {

        String firstDate = DataGenerator.generateDate(3);
        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id='phone'] input").setValue("+7" + DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(".button").click();

        String secondDate = DataGenerator.generateDate(10);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(Condition.visible).shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(".notification_status_error .button").click();
        $(withText("Ура!")).shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible).shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondDate));
    }
}
