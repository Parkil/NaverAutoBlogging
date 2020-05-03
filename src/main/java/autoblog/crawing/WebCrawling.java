package autoblog.crawing;

import java.util.List;

/*
Crawling - Interface
        GoogleCrawling
        NaverCrawling
        DaumCrawling
        ....

        Interface에 등록할 동작
        검색페이지 로드 void
        검색페이지 완전로딩 여부 판단 boolean
        검색어 입력 void
        검색결과페이지 로드 void
        검색결과 페이지 완전 로딩시까지 대기 boolean
        검색결과페이지에서 url추출 void
        검색결과페이지 에서 페이지 이동(하단 페이징이용) (대기는 위의 로딩시 대기를 이용) void
        */
public interface WebCrawling {
    void init(); //초기화

    void loadSearchPage(); //검색페이지 로드

    boolean isCompleteLoadSearchPage(); //검색페이지가 완전히 로드되었는지 판단

    void insertSearchWord(String keyword); //검색어 입력 + 검색이벤트 발생

    boolean isCompleteLoadResultPage(); //검색결과페이지가 완전히 로드되었는지 판단

    List<String> getUrlList(); //검색결과 페이지에서 URL리스트 반환

    void goResultPageNum(int pageNumber); //검색결과 페이지내에서 페이지 이동

    void complete(); //크롤링 종료
}