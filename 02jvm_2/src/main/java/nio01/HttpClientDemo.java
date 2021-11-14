package nio01;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @ClassName: nio01.HttpClientDemo
 * @Description:
 * @Author caoxunan | caoxunan@nullht.com
 * @date 11/14/21 9:22 PM
 * @Version 1.0
 */
public class HttpClientDemo {

    public static void main(String[] args) throws IOException {

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8081")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

}
