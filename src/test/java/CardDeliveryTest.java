import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

//gradlew test -Dselenide.headless=true
public class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void formFilling() {
        String planningDate = generateDate(10);

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов-Ивин Трофим");
        $("[data-test-id='phone'] input").setValue("+79368756320");
        $("[data-test-id=agreement]").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains (text(),'Успешно!')]")
                .shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),
                        Duration.ofSeconds(15)).shouldBe(Condition.visible);

    }

}
