package br.com.facilit.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/feature",
        glue = {"br.com.facilit.cucumber", "br.com.facilit.config"},
        plugin = {
                "pretty",
                "summary",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json"
        },
        monochrome = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        publish = false
)
public class CucumberIT {
}
