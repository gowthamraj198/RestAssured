package options;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty","html:output/cucumber-htmlreport","json:output/cucumber-report.json"},
        glue = {"stepdefs"},
        features = {"src/test/java/features"})
public class CucumberTest {
}
