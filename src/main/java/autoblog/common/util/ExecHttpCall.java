package autoblog.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;

public class ExecHttpCall {
    public String execHttpCall(String url, Map<String,String> paramInfo, Map<String,String> headerInfo, int time_out, String httpMethod) throws IOException {
        List<NameValuePair> param_list = new ArrayList<>();

        if(paramInfo != null && paramInfo.size() != 0) {
            for (String key : paramInfo.keySet()) {
                String val = paramInfo.get(key);
                param_list.add(new BasicNameValuePair(key, val));
            }
        }

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(time_out)
                .setConnectTimeout(time_out).setSocketTimeout(time_out).setCookieSpec(CookieSpecs.STANDARD).build();

        HttpUriRequest request;

        switch (httpMethod.toUpperCase()) {
            case "GET" :
                String fullUrl = url + "?" + URLEncodedUtils.format(param_list, "utf-8");
                request = new HttpGet(fullUrl);
                break;
            case "POST" :
                request = new HttpPost(url);
                ((HttpPost)request).setEntity(new UrlEncodedFormEntity(param_list, "utf-8"));
                break;
            default:
                throw new IOException("Invalid Http Method");
        }

        if(headerInfo != null && headerInfo.size() != 0) {
            List<Header> headerList = new ArrayList<>();

            for (String key : headerInfo.keySet()) {
                String val = headerInfo.get(key);
                headerList.add(new BasicHeader(key, val));
            }

            request.setHeaders(headerList.toArray(new Header[0]));
        }

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        CloseableHttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        String retStr = IOUtils.toString(entity.getContent(), "utf-8");

        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(retStr);
        return retStr;
    }
}