package limod.de.remotecontrolclient.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominic on 21.11.16.
 */
public class ServiceSingleton {

    private SoundService soundService;
    private SpotifyService spotifyService;

    private List<RestService> restServiceList = new ArrayList<>();

    public void setHost(String host){
        if(restServiceList != null) {
            for (RestService service : restServiceList) {
                service.setHost(host);
            }
        }
    }

    public ServiceSingleton() {
        this.setSoundService(new SoundService());
        restServiceList.add(this.getSoundService());
        this.setSpotifyService(new SpotifyService());
        restServiceList.add(this.getSpotifyService());
    }


    public SoundService getSoundService() {
        if(soundService == null){
            soundService = new SoundService();
            restServiceList.add(soundService);
        }
        return soundService;
    }

    public void setSoundService(SoundService soundService) {
        this.soundService = soundService;
    }

    public SpotifyService getSpotifyService() {
        if(spotifyService == null){
            spotifyService = new SpotifyService();
            restServiceList.add(spotifyService);
        }
        return spotifyService;
    }

    public void setSpotifyService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }
}
