package APISteps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

@Tag("TEST")
public class ReqAPISteps {
    public static JSONObject body;
    @Дано("JSON файл")
    public  void JSON_файл() throws IOException {
        body = new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/name.json"))));
    }

    @Тогда("редактирование")
    public void редактирование() {
        body.put("name","potato");
        body.put("job","Eat Maket");
    }
    @Затем("отправка")
    public void отправка(){
        Response postJSON = given()
                .filter(new AllureRestAssured())
                .header("Content-Type","application/json")
                .baseUri("https://reqres.in/api")
                .body(body.toString())
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();
    }
}
