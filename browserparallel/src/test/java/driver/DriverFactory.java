package driver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory {

	private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	private static ThreadLocal<String> browsers = new ThreadLocal<>();

	// To quit the drivers and browsers at the end only.
	private static List<WebDriver> storedDrivers = new ArrayList<>();

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				storedDrivers.forEach(WebDriver::quit);
			}
		});
	}

	private DriverFactory() {
	}

	public static String getBrowser() {
		return browsers.get();
	}

	public static void setBrowser(String browser) {
		browsers.set(browser);
	}

	public static WebDriver getDriver() {
		if (drivers.get() == null) {
			WebDriver driver = null;
			
			if (DriverFactory.getBrowser().equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (DriverFactory.getBrowser().equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			
			driver.manage().window().maximize();
			addDriver(driver);
		}
		return drivers.get();
	}

	public static void addDriver(WebDriver driver) {
		storedDrivers.add(driver);
		drivers.set(driver);
	}

	public static void removeDriver() {
		storedDrivers.remove(drivers.get());
		drivers.remove();
	}
}