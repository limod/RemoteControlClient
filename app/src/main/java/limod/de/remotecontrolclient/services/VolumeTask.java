package limod.de.remotecontrolclient.services;

import android.os.AsyncTask;
import android.util.Log;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dominic on 22.05.16.
 */
public class VolumeTask extends AsyncTask<VolumeTask.Action, Void, String> {


    private static final String TAG = "VolumeTask";

    public static enum Action {
        UP, DOWN, MUTE
    }

    @Override
    protected String doInBackground(Action... params) {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target("http://192.168.1.11:8080/myapp");
        WebTarget webResource = webTarget.path("volume");
        WebTarget webAction = null;

        if (params[0] == Action.UP) {
            webAction = webResource.path("up");
        } else if (params[0] == Action.DOWN) {
            webAction = webResource.path("down");
        } else if (params[0] == Action.MUTE) {
            webAction = webResource.path("set");
        }

        try {
            if (webAction != null) {
                Invocation.Builder request = webAction.request(MediaType.APPLICATION_JSON_TYPE);


                Response response = request.get();
                Log.d(VolumeTask.TAG,"response status: " + response.getStatus());
                Float result = response.readEntity(Float.class);
                Log.d(VolumeTask.TAG,"enitity: " + result);

                if(result != null){
                    return "rsult";
                }
                return "null";
            }
        } catch (ProcessingException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(VolumeTask.TAG,"onPostExecute: " + result);
    }
}
