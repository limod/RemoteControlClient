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
public class SpotifyTask extends AsyncTask<SpotifyTask.Action, Void, String> {


    private static final String TAG = "SpotifyTask";

    public static enum Action {
        PLAY, PAUSE, NEXT, PREVIOUS
    }

    @Override
    protected String doInBackground(Action... params) {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target("http://192.168.1.11:8080/myapp");
        WebTarget webResource = webTarget.path("spotify");
        WebTarget webAction = null;

        if (params[0] == Action.PLAY) {
            webAction = webResource.path("play");
        } else if (params[0] == Action.PAUSE) {
            webAction = webResource.path("pause");
        } else if (params[0] == Action.NEXT) {
            webAction = webResource.path("next");
        } else if (params[0] == Action.PAUSE) {
            webAction = webResource.path("previous");
        }

        try {
            if (webAction != null) {
                Invocation.Builder request = webAction.request(MediaType.APPLICATION_JSON_TYPE);


                Response response = request.get();
                Log.d(SpotifyTask.TAG,"response status: " + response.getStatus());
                String result = response.readEntity(String.class);
                Log.d(SpotifyTask.TAG,"enitity: " + result);

                if(result != null){
                    return result;
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
        Log.d(SpotifyTask.TAG,"onPostExecute: " + result);
    }
}
