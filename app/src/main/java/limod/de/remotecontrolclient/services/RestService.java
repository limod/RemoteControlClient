package limod.de.remotecontrolclient.services;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by dominic on 16.11.16.
 */
public abstract class RestService {

    private String host;

    public  String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBaseUrl() {
        return String.format("http://%s:%s/", this.getHost(), "8080"); // "http://192.168.2.101:8080/\";"
    }

    public String getAbsoluteUrl(String serviceUrl, String relativeUrl) {
        return (getBaseUrl() + serviceUrl + relativeUrl).trim();
    }

    abstract void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
}
