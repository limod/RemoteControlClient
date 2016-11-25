package limod.de.remotecontrolclient.services;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import limod.de.remotecontrolclient.CustomAsynHttpClient;

/**
 * Created by dominic on 31.07.16.
 */
public class SpotifyService extends RestService {

    private static final String SERVICE_URL =  "myapp/spotify/";
    public static final String API_PLAY = "play";
    public static final String API_PAUSE = "pause";
    public static final String API_NEXT= "next";
    public static final String API_PREVIOUS = "previous";
    private static CustomAsynHttpClient client = new CustomAsynHttpClient();



    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(SERVICE_URL, url), params, responseHandler);
    }


}
