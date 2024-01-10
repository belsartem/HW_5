import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SoftAssertionsPageTest {

    private String firstJUnitSnippet = "@ExtendWith({SoftAssertsExtension.class})\n" +
            "class Tests {\n" +
            "  @Test\n" +
            "  void test() {\n" +
            "    Configuration.assertionMode = SOFT;\n" +
            "    open(\"page.html\");\n" +
            "\n" +
            "    $(\"#first\").should(visible).click();\n" +
            "    $(\"#second\").should(visible).click();\n" +
            "  }\n" +
            "}";

    private String secondJUnitSnippet = "class Tests {\n" +
            "  @RegisterExtension \n" +
            "  static SoftAssertsExtension softAsserts = new SoftAssertsExtension();\n" +
            "\n" +
            "  @Test\n" +
            "  void test() {\n" +
            "    Configuration.assertionMode = SOFT;\n" +
            "    open(\"page.html\");\n" +
            "\n" +
            "    $(\"#first\").should(visible).click();\n" +
            "    $(\"#second\").should(visible).click();\n" +
            "  }\n" +
            "}";

    @BeforeEach
    void beforeEachSettings() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://github.com/";
    }

    @Test
    void TestAssertionsPage() {
        open("selenide/selenide/");
        $("#wiki-tab").click();
        $("#user-content-welcome-to-the-selenide-wiki").shouldBe(visible);
        $("#wiki-pages-box").shouldNotHave(text("SoftAssertions"));
        $("#wiki-pages-box [type='button']").click();
        $("#wiki-pages-box").shouldHave(text("SoftAssertions")).shouldBe(visible);
        $("#wiki-pages-box").$(byText("SoftAssertions")).click();
        $("#user-content-3-using-junit5-extend-test-class").ancestor("h4").sibling(0).shouldHave(text(firstJUnitSnippet));
        $("#user-content-3-using-junit5-extend-test-class").ancestor("h4").sibling(2).shouldHave(text(secondJUnitSnippet));
    }
}