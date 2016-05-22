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
import limod.de.remotecontrolclient.services.SpotifyTask;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppCompatActivity";

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

            WebTarget webTarget = client.target("http://192.168.1.11:8080/myapp");
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
