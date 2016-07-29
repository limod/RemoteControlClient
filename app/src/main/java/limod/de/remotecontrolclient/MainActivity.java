package limod.de.remotecontrolclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import limod.de.remotecontrolclient.services.SpotifyTask;
import limod.de.remotecontrolclient.services.VolumeTask;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String HOST = "http://192.168.0.25:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new SpotifyTask().execute(SpotifyTask.Action.PLAY);

            }
        });

        Button btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new SpotifyTask().execute(SpotifyTask.Action.PAUSE);

            }
        });

        Button btnNext= (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new SpotifyTask().execute(SpotifyTask.Action.NEXT);

            }
        });

        Button btnDown= (Button) findViewById(R.id.btnVolumeDown);
        btnDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new VolumeTask().execute(VolumeTask.Action.DOWN);

            }
        });

        Button btnUp= (Button) findViewById(R.id.btnVolumeUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

              //  new VolumeTask().execute(VolumeTask.Action.UP);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(MainActivity.HOST + "myapp/volume/up", new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                        Log.d(MainActivity.TAG,"onStart");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        Log.d(MainActivity.TAG,"onSuccess");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.d(MainActivity.TAG,"onFailure");
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                        Log.d(MainActivity.TAG,"onRetry");
                    }
                });

            }
        });


        Button btnMute= (Button) findViewById(R.id.btnVolumeMute);
        btnMute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //  new VolumeTask().execute(VolumeTask.Action.UP);
                RequestParams params = new RequestParams();
                params.put("volume", "0.5");
                String host = MainActivity.HOST + "myapp/volume/set";

                Log.d(MainActivity.TAG, "host: " + host);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(host, params,  new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                        Log.d(MainActivity.TAG,"onStart");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        Log.d(MainActivity.TAG,"onSuccess");
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.d(MainActivity.TAG,"onFailure");
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                        Log.d(MainActivity.TAG,"onRetry");
                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, Void> {

        private Exception exception;

        @Override
        protected Void doInBackground(Void... params) {

            ClientConfig clientConfig = new ClientConfig();
            Client client = ClientBuilder.newClient(clientConfig);

            WebTarget webTarget = client.target(MainActivity.HOST + "myapp");
            WebTarget vlcResource = webTarget.path("spotify");
            WebTarget vlcTest = vlcResource.path("pause");

            Invocation.Builder request = vlcTest.request(MediaType.APPLICATION_JSON_TYPE);
            try {
                Response response = request.get();
                System.out.println(response.getStatus());
                System.out.println(response.readEntity(String.class));
            } catch (ProcessingException ex) {
                System.out.println(ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            System.out.println("post execute");
            Log.d(MainActivity.TAG, "post execute");

        }
    }
}
