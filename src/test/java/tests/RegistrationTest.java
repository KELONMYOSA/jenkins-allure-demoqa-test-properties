package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@DisplayName("Тесты на demoqa.com")
public class RegistrationTest extends TestBase {
    @Test
    @Owner("KELONMYOSA")
    @Feature("Тесты на demoqa.com")
    @DisplayName("Проверяем регистрационную форму")
    @Link(value = "Testing URL", url = "https://demoqa.com/automation-practice-form")
    void FillFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем страницу формы", () -> open("/automation-practice-form"));

        step("Заполняем имя, фамилю, email, номер телефона и адрес", () -> {
            $("#firstName").setValue("Name");
            $("#lastName").setValue("LastName");
            $("#userEmail").setValue("test@mail.ru");
            $("#userNumber").setValue("0123456789");
            $("#currentAddress").setValue("Ulica, dom 1");
        });

        step("Выбираем пол", () -> $(byText("Male")).click());

        step("Выбираем дату рождения через календарь", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue("0");
            $(".react-datepicker__year-select").selectOptionByValue("2000");
            $(".react-datepicker__week ").$(byText("1")).click();
        });

        step("Выбираем предметы и улечения", () -> {
            $("#subjectsInput").setValue("Maths").pressEnter();
            $(byText("Sports")).click();
        });

        //step("Загружаем картинку", () -> $("#uploadPicture").uploadFromClasspath("test-pic.jpg"));

        step("Выбираем штат и город", () -> {
            $("#state").click();
            $(byText("NCR")).click();
            $("#city").click();
            $(byText("Delhi")).click();
        });

        step("Подтверждаем отправку формы", () -> $("#submit").click());

        step("Проверяем корректность данных", () -> {
            $(".table-responsive").shouldHave(
                    text("Name LastName"),
                    text("test@mail.ru"),
                    text("Male"),
                    text("0123456789"),
                    text("01 January,2000"),
                    text("Maths"),
                    text("Sports"),
                    //text("test-pic.jpg"),
                    text("Ulica, dom 1"),
                    text("NCR Delhi")
            );
        });
    }
}
