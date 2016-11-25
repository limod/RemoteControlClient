package limod.de.remotecontrolclient.services;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import limod.de.remotecontrolclient.CustomAsynHttpClient;

/**
 * Created by dominic on 31.07.16.
 */
public class SoundService extends RestService {

    private static String SERVICE_URL = "myapp/sound/";
    private static String URL =  "myapp/sound/";
    public static final String API_SET_VOLUME = "set";
    public static final String API_MUTE = "mute";
    private static CustomAsynHttpClient client = new CustomAsynHttpClient();


    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(SERVICE_URL, url), params, responseHandler);
    }


}
