package limod.de.remotecontrolclient;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by dominic on 01.08.16.
 */
public class CustomAsynHttpClient extends AsyncHttpClient {

    public CustomAsynHttpClient() {
        this.setMaxRetriesAndTimeout(0,0);
    }
}
