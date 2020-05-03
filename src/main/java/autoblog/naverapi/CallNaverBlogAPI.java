package autoblog.naverapi;

import autoblog.common.util.ExecHttpCall;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CallNaverBlogAPI {
    private final static String client_id = "ylwW_Inz8Pd6XaobaGT2";//애플리케이션 클라이언트 아이디값";
    private final static String client_secret = "teaGmnLj7O";//애플리케이션 클라이언트 시크릿값";
    private final static String refresh_token = "zONS1ZnVCmMoaIyR0WipHxLn1cqM6ipkwUIIZqnmipKBJpdo0iskYf1ZPjQFuvbwHBOavXwDl04gC0iizA8dJBnzBVsguis9QmhM9KHA2JGCUcakR5bvNudroeQP06RwrMIuAX"; //토큰갱신용

    //네이버 블로그 등록을 위한 토큰 갱신
    private Map<String,String> refreshToken() {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "refresh_token");
        paramMap.put("client_id", client_id);
        paramMap.put("client_secret", client_secret);
        paramMap.put("refresh_token", refresh_token);

        String retStr = null;
        try {
            retStr = new ExecHttpCall().execHttpCall("https://nid.naver.com/oauth2.0/token", paramMap, null, 10000, "GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(retStr, Map.class);
    }

    //네이버 블로그 글 등록
   public Map<String,String> registerNaverBlog(String subject, String contents) {
       String accessToken = refreshToken().get("access_token");

       Map<String,String> headerInfo = new HashMap<>();
       headerInfo.put("Authorization", "Bearer "+accessToken);

       Map<String,String> param = new HashMap<>();
       param.put("title", subject);
       param.put("contents", contents);

       String retStr = null;
       try {
           retStr = new ExecHttpCall().execHttpCall("https://openapi.naver.com/blog/writePost.json", param, headerInfo, 10000, "POST");
           System.out.println("retStr : "+retStr);
       } catch (IOException e) {
           e.printStackTrace();
       }

       Gson gson = new Gson();
       return gson.fromJson(retStr, Map.class);
   }
}