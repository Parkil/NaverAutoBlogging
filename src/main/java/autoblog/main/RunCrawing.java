package autoblog.main;

import autoblog.common.util.FormatUrlContents;
import autoblog.crawing.GoogleCrawing;
import autoblog.crawing.GoogleTrendCrawing;
import autoblog.crawing.WebCrawling;
import autoblog.naverapi.CallNaverBlogAPI;
import java.util.List;

public class RunCrawing {
    /*
    현재까지의 진행상황
        1.google trend에서 일일 검색어 순위를 가져와서 google검색을 수행하여 네이버 블로그에 저장
    추가로 구현되어야 하는 부분(장기과제)
        1.google검색을 수행하여 가져온 문자데이터를 식별하여 가장 적합한 검색결과를 선정
            - 이부분에서 머신러닝이 기술이 들어갈것으로 예측되는데 현재 머신러닝에 대한 이해가 전무한 관계로 학습하는데 시간이 필요
        2.1번에서 가져온 데이터를 네이버블로그에 올리기 좋게 가공하는 과정
            - 문자데이터를 식별하여 필요한 부분을 판단하는기술(머신러닝이 필요할것으로 예측), 판단된 문자열을 formating하는 기술
            (기존기술을 검색하되 없으면 직접구현도 고려)
     */
    public static void main(String[] args) {
        //google trend에서 최신 유행 검색어 추출
        WebCrawling crawling = new GoogleTrendCrawing();
        crawling.init();
        crawling.loadSearchPage();
        crawling.isCompleteLoadSearchPage();
        crawling.insertSearchWord("테스트");
        crawling.isCompleteLoadResultPage();
        List<String> keywordList = crawling.getUrlList();
        crawling.complete();

        for(String keyword : keywordList) {
            System.out.println("검색어 : "+keyword);
        }

        System.out.println(keywordList.subList(0,5));

        //유행검색어로 Google검색을 수행하여 첫번째 검색결과를 네이버 블로그에 저장
        crawling = new GoogleCrawing();
        crawling.init();
        crawling.loadSearchPage();
        crawling.isCompleteLoadSearchPage();

        CallNaverBlogAPI api = new CallNaverBlogAPI();

        for(String keyword : keywordList.subList(0,5)) {
            crawling.insertSearchWord(keyword);
            crawling.isCompleteLoadResultPage();
            List<String> urlList = crawling.getUrlList();

            String url = urlList.get(0);

            String contents = FormatUrlContents.getBodyHtml(url);
            api.registerNaverBlog(keyword, contents);
        }
        crawling.complete();
    }
}
