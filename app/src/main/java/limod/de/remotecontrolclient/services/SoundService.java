package limod.de.remotecontrolclient.services;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import limod.de.remotecontrolclient.CustomAsynHttpClient;
import limod.de.remotecontrolclient.MainActivity;

/**
 * Created by dominic on 31.07.16.
 */
public class SoundService {

    private static final String BASE_URL = MainActivity.HOST + "myapp/sound/";
    public static final String API_SET_VOLUME = "set";
    public static final String API_MUTE = "mute";
    private static CustomAsynHttpClient client = new CustomAsynHttpClient();




    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
