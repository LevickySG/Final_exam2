package hooks;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ApiHooks implements BeforeAllCallback {
    @BeforeEach
    public void allureStarter(){
        String listenerName = "AllureSelenide";
        if(!(SelenideLogger.hasListener(listenerName)))
            SelenideLogger.addListener(listenerName,
                    new AllureSelenide().screenshots(true).savePageSource(false));
    }
    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context){
        if(!started){
            started =   true;
            RestAssured.filters(new AllureRestAssured());
        }
    }
}
