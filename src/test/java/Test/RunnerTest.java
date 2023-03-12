package Test;

import hooks.ApiHooks;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty","io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
        features = "src/test/java/features",
        glue = {"APISteps","hooks"},
        tags = "@TEST"
)
public class RunnerTest extends ApiHooks {
}