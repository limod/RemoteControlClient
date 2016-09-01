package limod.de.remotecontrolclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;

/**
 * Created by dominic on 01.08.16.
 */
public class CustomAsynHttpClient extends AsyncHttpClient {

    public CustomAsynHttpClient() {
        this.setBasicAuth("foobar","shithead");
        this.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());

        this.setMaxRetriesAndTimeout(0,0);
    }
}
