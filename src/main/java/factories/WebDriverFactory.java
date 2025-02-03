package factories;


import exceptions.BrowserNotFoundException;
import factories.settings.ChromeSettings;
import factories.settings.FirefoxSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    private String browserName = System.getProperty("browser");

    public WebDriver create() {
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = (ChromeOptions) new ChromeSettings().settings();
                return new ChromeDriver(chromeOptions);
            case "firefox":
                FirefoxOptions firefoxOptions = (FirefoxOptions) new FirefoxSettings().settings();
                return new FirefoxDriver(firefoxOptions);
            default:
                throw new BrowserNotFoundException(browserName);
        }
    }
}
