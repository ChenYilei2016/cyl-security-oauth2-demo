package mytest;

import jdk.internal.net.http.RequestPublishers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.Duration;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/27- 16:10
 */
public class RestTestUtil {
    static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(50))
            //.proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
            //.authenticator(Authenticator.getDefault())
            .build();

    public static Object get(String url){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
//                .PUT(new RequestPublishers.StringPublisher())
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
