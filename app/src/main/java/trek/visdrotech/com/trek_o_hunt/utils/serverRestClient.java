package trek.visdrotech.com.trek_o_hunt.utils;

/**
 * Created by defcon on 09/06/18.
 */
import com.loopj.android.http.*;
public class serverRestClient {
    private static final String BASE_URL = "http://104.199.139.155:8000/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
