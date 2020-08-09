package driver;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;

public class Stepdefs {
	
	@Before("@chrome")
	public void beforeChrome() {
		DriverFactory.setBrowser("chrome");
	}
	
	@Before("@firefox")
	public void beforeFirefox() {
		DriverFactory.setBrowser("firefox");
	}
	
	@And("Go to {word}")
	public void visitweb(String site) throws Exception {
		System.out.println(site + " " + DriverFactory.getBrowser() + " " + Thread.currentThread().getId());
		DriverFactory.getDriver().get(site);
		Thread.sleep(2000);
	}
	
}