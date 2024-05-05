package gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class SoundPlayer {
    private String musicFile;
    private Media sound;
    private MediaPlayer mediaPlayer;
    private boolean playingStatus;

    public SoundPlayer(String musicFileURL){
        try{
            musicFile = musicFileURL;
            sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
            setPlayingStatus(false);
        }
        catch(Exception e){
            System.out.println("Can't read the file");
            System.out.println(e);
        }
    }

    public void setLoop(){
        try{
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        }
        catch(Exception e){
            System.out.println("Can't loop the music");
        }
    }

    public void startPlay(){
        try{
            mediaPlayer.play();
            setPlayingStatus(true);
        }
        catch(Exception e){
            System.out.println("Can't play the music");
            System.out.println(e);
        }
    }

    public void stopPlay(){
        try{
            mediaPlayer.stop();
            setPlayingStatus(false);
        }
        catch(Exception e){
            System.out.println("Can't stop playing the music");
            System.out.println(e);
        }
    }

    public void setVolume(double volume){
        mediaPlayer.setVolume(volume);
    }

    public void setPlayingStatus(boolean status){
        this.playingStatus = status;
    }

    public boolean isPlaying(){
        return playingStatus;
    }
}
