package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Hello world!
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(
		glue = "stepDefs",
		features = "features"
)
public class CucumberTest 
{
}
