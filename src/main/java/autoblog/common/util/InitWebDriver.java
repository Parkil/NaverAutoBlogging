package autoblog.common.util;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class InitWebDriver {
    /** 크롬 브라우저를 이용하는 WebDriver 설정
     * @return WebDriver 객체
     */
    public static WebDriver initChromeDriver() {
        StringBuffer sb = new StringBuffer();

        sb.append(System.getProperty("user.dir"));
        sb.append(File.separator);
        sb.append("src");
        sb.append(File.separator);
        sb.append("test");
        sb.append(File.separator);
        sb.append("resources");
        sb.append(File.separator);
        sb.append("chromedriver.exe");

        System.setProperty("webdriver.chrome.driver", sb.toString()); //driver위치 반환
        ChromeOptions co = new ChromeOptions();
        co.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        co.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        //co.addArguments("--headless"); // only if you are ACTUALLY running headless
        co.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        co.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        co.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        co.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        co.addArguments("--disable-gpu");
        co.setBinary("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"); //cannot find chrome binary 예외 발생시에는 chrome실행파일을 수동으로 지정해 주어야 함
        return new ChromeDriver(co);
    }
}
