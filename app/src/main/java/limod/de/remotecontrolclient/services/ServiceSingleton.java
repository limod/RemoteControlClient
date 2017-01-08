package limod.de.remotecontrolclient.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominic on 21.11.16.
 */
public class ServiceSingleton {

    private SoundService soundService;
    private SpotifyService spotifyService;
    private VlcService vlcService;
    private TvService tvService;

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
        this.setVlcService(new VlcService());
        restServiceList.add(this.getVlcService());
        this.setTvService(new TvService());
        restServiceList.add(this.getTvService());
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

    public VlcService getVlcService() {
        if(vlcService == null){
            vlcService = new VlcService();
            restServiceList.add(vlcService);
        }
        return vlcService;
    }

    public void setVlcService(VlcService vlcService) {
        this.vlcService = vlcService;
    }

    public TvService getTvService() {
        if(tvService == null){
            tvService = new TvService();
            restServiceList.add(tvService);
        }
        return tvService;

    }

    public void setTvService(TvService tvService) {
        this.tvService = tvService;
    }
}
