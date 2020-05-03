package autoblog.crawing;

import autoblog.common.util.InitWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class GoogleCrawing implements WebCrawling {
    private WebDriver webDriver;
    private WebDriverWait wdw;

    @Override
    public void init() {
        webDriver = InitWebDriver.initChromeDriver();
        wdw = new WebDriverWait(webDriver, 10);
    }

    @Override
    public void loadSearchPage() {
        webDriver.get("http://www.google.co.kr");
    }

    @Override
    public boolean isCompleteLoadSearchPage() {
        wdw.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[title=검색]")));
        return true;
    }

    @Override
    public void insertSearchWord(String keyword) {
        WebElement el = webDriver.findElement(By.cssSelector("input[title=검색]"));
        el.clear();
        el.sendKeys(keyword);
        el.sendKeys(Keys.ENTER);
    }

    @Override
    public boolean isCompleteLoadResultPage() {
        wdw.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#result-stats")));
        return true;
    }

    @Override
    public List<String> getUrlList() {
        List<WebElement> list = webDriver.findElements(By.xpath("//div[@id=\"search\"]/descendant::a"));
        return list.stream().filter(e ->
                   !e.getAttribute("href").startsWith("https://webcache.googleusercontent.com")
                && !e.getAttribute("href").startsWith("https://www.google.co.kr/search")
                && !e.getAttribute("href").startsWith("http://webcache.googleusercontent.com")
                ).map(t -> t.getAttribute("href")).collect(Collectors.toList());
    }

    @Override
    public void goResultPageNum(int pageNumber) {

    }

    @Override
    public void complete() {
        webDriver.close();
    }
}
