package dash.network;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Quincy on 15/9/25.
 */
public class NetworkConnection {
    public static final String SEGMENT_UPLOAD_URL = "http://pilatus.d1.comp.nus.edu.sg/~team01/upload.php";
    public static final String MOVIES_RETRIEVE_URL = "http://pilatus.d1.comp.nus.edu.sg/~team01/watch.php";
    private HttpClient httpClient;
    private HttpPost httpPost;
    private MultipartEntity multipartEntity;

    public static ExchangeData getMP4(String mp4URL) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(mp4URL);
        return new Gson().fromJson(EntityUtils.toString(httpClient.execute(httpGet).getEntity()), ExchangeData.class);
    }

    public static InputStream getMPD(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        return httpClient.execute(httpGet).getEntity().getContent();
    }

    public NetworkConnection() {
        this.httpClient = new DefaultHttpClient();
        multipartEntity = new MultipartEntity();
    }

    public NetworkConnection makePost(String url) {
        httpPost = new HttpPost(url);
        return this;
    }

    public NetworkConnection addFileBody(File file) {
        multipartEntity.addPart("uploadFile", new FileBody(file));
        return this;
    }

    public NetworkConnection addExchangeData(ExchangeData segmentInfo) throws UnsupportedEncodingException {
        multipartEntity.addPart("json", new StringBody(new Gson().toJson(segmentInfo)));
        return this;
    }

    public ExchangeData jsonResponse() throws IOException {
        String entity = EntityUtils.toString(doRequest().getEntity());
        return new Gson().fromJson(entity, ExchangeData.class);
    }

    public HttpResponse doRequest() throws IOException {
        httpPost.setEntity(multipartEntity);
        return httpClient.execute(httpPost);
    }
}
