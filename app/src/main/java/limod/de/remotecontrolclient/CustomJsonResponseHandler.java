package limod.de.remotecontrolclient;

import android.support.v4.app.FragmentManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dominic on 01.08.16.
 */
public class CustomJsonResponseHandler extends JsonHttpResponseHandler {

    FragmentManager fm;

    public CustomJsonResponseHandler(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        try {
            if(errorResponse != null && errorResponse.getString("message") != null) {
                showDialog(errorResponse.getString("message"));
            } else {
                showDialog(throwable.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDialog(String message){

        InfoDialog dialog = InfoDialog.newInstance(message);
        dialog.show(fm, "Dunno");
    }
}
