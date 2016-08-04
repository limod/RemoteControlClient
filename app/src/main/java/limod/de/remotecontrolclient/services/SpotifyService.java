package limod.de.remotecontrolclient.services;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import limod.de.remotecontrolclient.CustomAsynHttpClient;
import limod.de.remotecontrolclient.MainActivity;

/**
 * Created by dominic on 31.07.16.
 */
public class SpotifyService {

    private static final String BASE_URL = MainActivity.HOST + "myapp/spotify/";
    public static final String API_PLAY = "play";
    public static final String API_PAUSE = "pause";
    public static final String API_NEXT= "next";
    public static final String API_PREVIOUS = "previous";
    private static CustomAsynHttpClient client = new CustomAsynHttpClient();



    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
