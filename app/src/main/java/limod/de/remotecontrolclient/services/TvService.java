package limod.de.remotecontrolclient.services;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import limod.de.remotecontrolclient.CustomAsynHttpClient;
import limod.de.remotecontrolclient.MainActivity;

/**
 * Created by dominic on 31.07.16.
 */
public class TvService extends RestService {

    private static final String SERVICE_URL = "myapp/tv/";
    public static final String API_SET_CHANNEL = "setChannel";
    public static final String API_PLAY_LAST_CHANNEL = "playLastChannel";
    public static final String API_NEXT= "next";
    public static final String API_PREVIOUS = "previous";
    public static final String API_TOGGLE_OSD = "toggleOsd";
    public static final String API_START = "start";
    public static final String API_QUIT = "quit";
    public static final String API_TOGGLE_FULLSCREEN = "toggleFullscreen";


    private static CustomAsynHttpClient client = new CustomAsynHttpClient();

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(SERVICE_URL, url), params, responseHandler);
    }


}
