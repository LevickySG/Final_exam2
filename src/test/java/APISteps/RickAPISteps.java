package APISteps;
import hooks.ApiHooks;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
@Tag("TEST")
public class RickAPISteps extends ApiHooks {
    public static Properties APIprop = new Properties();
    public static String CharID;
    public static String LastEpisodeId;
    public static String LastCharacterId;
    public static String MortyLoc;
    public static String MortySpec;
    public static String CharLoc;
    public static String CharSpec;

    @Дано("ID первого персонажа {String}")
    public static void IDПервогоПерсонажа() throws IOException {
        ApiHooks.allureStarter();
        APIprop.load(new FileInputStream("C:/Users/Magnus/IdeaProjects/" +
                                "/FInal_exam_1/src/test/resources/taskcreate.properties"));
        CharID = APIprop.getProperty("ID");
    }
    @Тогда("получение данных о первом персонаже")
    public static void получениеДанныхОПервомПерсонаже() {
        Response gettingChar = given().filter(new AllureRestAssured()).filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/character/" + CharID)
                .then()
                .extract()
                .response();
        String charName = (new JSONObject(gettingChar.getBody().asString()).get("name").toString());
        int episode = (new JSONObject(gettingChar.getBody().asString()).getJSONArray("episode").length() - 1);
        LastEpisodeId = new JSONObject(gettingChar.getBody().asString())
                .getJSONArray("episode").get(episode).toString().replaceAll("[^0-9]", "");
        MortySpec = new JSONObject(gettingChar.getBody().asString()).get("species").toString();
        MortyLoc = new JSONObject(gettingChar.getBody().asString()).getJSONObject("location").get("name").toString();

    }
    @Затем("айди последнего персонажа в последнем эпизоде")
    public static void айдиПоследнегоПерсонажа() {
        Response getCharFromEpisode = given()
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/episode/" + LastEpisodeId)
                .then()
                .extract()
                .response();
        int lastcharId = (new JSONObject(getCharFromEpisode.getBody().asString()).getJSONArray("characters").length() - 1);
        LastCharacterId = new JSONObject(getCharFromEpisode.getBody().asString())
                .getJSONArray("characters").get(lastcharId).toString().replaceAll("[^0-9]", "");
    }
    @Затем("получение информации о последнем персонаже")
    public static void получениеИнформацииОПоследнемПерсонаже() {
        Response gettingChar = given()
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/character/" + LastCharacterId)
                .then()
                .extract()
                .response();
        CharSpec = new JSONObject(gettingChar.getBody().asString()).get("species").toString();
        CharLoc = new JSONObject(gettingChar.getBody().asString()).getJSONObject("location").get("name").toString();
    }
    @И("сравнение данных о персонажах")
    public static void сравнениеДанныхОПерсонажах() {
        Assertions.assertAll(
                () -> assertEquals("Another Locations",MortyLoc,CharLoc),
                () -> assertEquals("Another Species",MortySpec, CharSpec)
        );
    }
}
