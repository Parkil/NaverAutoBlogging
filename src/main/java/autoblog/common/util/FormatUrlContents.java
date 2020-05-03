package autoblog.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FormatUrlContents {
    public static String getBodyHtml(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Element body = doc.body();
        body.getElementsByTag("script").remove(); //script 부분 삭제

        //img tag의 상대 url을 절대 URL로 변경
        Elements imgTag = body.getElementsByTag("img");

        for(Element el : imgTag) {
            String imgSrc = el.absUrl("src");
            el.attr("src",imgSrc);
        }

        return body.html();
    }
}