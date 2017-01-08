package limod.de.remotecontrolclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import limod.de.remotecontrolclient.services.*;
import limod.de.remotecontrolclient.settings.SettingsActivity;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private static final String TAG = "MainActivity";
    public static String HOST = "192.168.2.101";
    private SeekBar soundSeekBar;

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    private SharedPreferences prefs;
    private ServiceSingleton services;


    private void showDialog(String message) {
        FragmentManager fm = getSupportFragmentManager();
        InfoDialog dialog = InfoDialog.newInstance(message);
        dialog.show(fm, "Dunno");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // Host lesen
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String host = sharedPref.getString(SettingsActivity.HOST_ADRESS, "");

        // services initialisieren
        this.services = new ServiceSingleton();
        this.services.setHost(host);

        Log.d("foobar", host);

        initSound();

        initSpotify();

        initVlc();

        initKaffeine();

        //listener on changed sort order preference:
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                Log.d("test", "Settings key changed: " + key);
                if (key.equals(SettingsActivity.HOST_ADRESS)) {
                    if(services != null){
                        String host = prefs.getString(SettingsActivity.HOST_ADRESS, "");
                        services.setHost(host);
                    }
                }
                //   getLoaderManager().restartLoader(LOADER_ID, null, tCallbacks);

            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (prefs != null) {
            prefs.unregisterOnSharedPreferenceChangeListener(prefListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (prefs != null) {
            prefs.registerOnSharedPreferenceChangeListener(prefListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...

                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    private void initKaffeine() {
        final EditText txtChannel = (EditText) findViewById(R.id.txtChannel);

        ImageButton btnSetChannel = (ImageButton) findViewById(R.id.btnKaffeineSetChannel);
        btnSetChannel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (txtChannel.getText() != null && txtChannel.getText().length() > 0 && isInteger(txtChannel.getText().toString(), 10)) {
                    RequestParams params = new RequestParams();
                    params.put("channel", txtChannel.getText());


                    services.getTvService().post(TvService.API_SET_CHANNEL, params, new CustomJsonResponseHandler(getSupportFragmentManager()));
                } else {
                    showDialog("Kein Channel gesetzt");
                }

            }
        });


        ImageButton btnQuit = (ImageButton) findViewById(R.id.btnKaffeineQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                services.getTvService().post(TvService.API_QUIT, null, new CustomJsonResponseHandler(getSupportFragmentManager()));
            }
        });

        ImageButton btnNext = (ImageButton) findViewById(R.id.btnKaffeineNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                services.getTvService().post(TvService.API_NEXT, null, new CustomJsonResponseHandler(getSupportFragmentManager()));
            }
        });

        ImageButton btnPrev = (ImageButton) findViewById(R.id.btnKaffeinePrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                services.getTvService().post(TvService.API_PREVIOUS, null, new CustomJsonResponseHandler(getSupportFragmentManager()));
            }
        });
        ImageButton btnLastChannel = (ImageButton) findViewById(R.id.btnKaffeineLastChannel);
        btnLastChannel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                services.getTvService().post(TvService.API_PLAY_LAST_CHANNEL, null, new CustomJsonResponseHandler(getSupportFragmentManager()));
            }
        });


    }


    private void initSound() {
        soundSeekBar = (SeekBar) findViewById(R.id.seekBar);

        /**
         * Sound Buttons
         */
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int originalProgress;

            @Override
            public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(MainActivity.TAG, "onProgressChanged");

                RequestParams params = new RequestParams();
                params.put("sound", 0.25 * (Double.valueOf(progress) / 10.0));

                services.getSoundService().post(SoundService.API_SET_VOLUME, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        try {
                            if (errorResponse != null && errorResponse.getString("message") != null) {
                                seekBar.setProgress(originalProgress);
                                seekBar.setEnabled(false);
                                showDialog(errorResponse.getString("message"));
                            } else {
                                showDialog(throwable.getMessage());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(MainActivity.TAG, "onStartTrackingTouch");
                originalProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(MainActivity.TAG, "onStopTrackingTouch");
            }
        });


        ImageButton btnMute = (ImageButton) findViewById(R.id.btnVolumeMute);
        btnMute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                services.getSoundService().post(SoundService.API_MUTE, null, new CustomJsonResponseHandler(getSupportFragmentManager()));
            }
        });


    }

    private void initSpotify() {
        /**
         * Spotify Buttons
         */
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnSpotifyPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                services.getSpotifyService().post(SpotifyService.API_PLAY, null, new CustomJsonResponseHandler(getSupportFragmentManager()));

            }
        });

        ImageButton btnNext = (ImageButton) findViewById(R.id.btnSpotifyNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                services.getSpotifyService().post(SpotifyService.API_NEXT, null, new CustomJsonResponseHandler(getSupportFragmentManager()));

            }
        });

        ImageButton btnPrevious = (ImageButton) findViewById(R.id.btnSpotifyPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                services.getSpotifyService().post(SpotifyService.API_PREVIOUS, null, new CustomJsonResponseHandler(getSupportFragmentManager()));

            }
        });
    }


    private void initVlc() {
        /**
         * VLC Buttons
         */

        ImageButton btnVlcPlay = (ImageButton) findViewById(R.id.btnVlcPlay);
        btnVlcPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                services.getVlcService().post(VlcService.API_PLAY, null, new CustomJsonResponseHandler(getSupportFragmentManager()));

            }
        });

        ImageButton btnVlcPause = (ImageButton) findViewById(R.id.btnVlcPause);
        btnVlcPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                services.getVlcService().post(VlcService.API_PAUSE, null, new CustomJsonResponseHandler(getSupportFragmentManager()));

            }
        });
        ImageButton btnVlcSeekB = (ImageButton) findViewById(R.id.btnVlcSeekB);
        btnVlcSeekB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.put("direction", -1);

                services.getVlcService().post(VlcService.API_SEEK, params, new CustomJsonResponseHandler(getSupportFragmentManager()));


            }
        });

        ImageButton btnVlcSeekF = (ImageButton) findViewById(R.id.btnVlcSeekF);
        btnVlcSeekF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.put("direction", +1);

                services.getVlcService().post(VlcService.API_SEEK, params, new CustomJsonResponseHandler(getSupportFragmentManager()));

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
    public void onDismiss(DialogInterface dialog) {
        Log.d(MainActivity.TAG, "onDismiss");
        soundSeekBar.setEnabled(true);
    }


}
