package mx.com.nullpointer.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by mota on 2/27/18.
 */

public class MusicController {


    //Musica
    private com.badlogic.gdx.audio.Music music;
    private static MusicController currentlyPlaying;
    private String songName;

    public MusicController(String song)
    {
        //"music/loop.mp3"
        //Añadiendo la musica
        music = Gdx.audio.newMusic(Gdx.files.internal(song));
        music.setLooping(true);
        music.setVolume(.5f);
        songName=song;
        playMusic(this);

    }
    public static void playMusic(MusicController playSong)
    {
        if(currentlyPlaying!= null && !currentlyPlaying.songName.equals(playSong.songName))
        {
            stopMusic();

        }
        else if(currentlyPlaying!= null && currentlyPlaying.songName.equalsIgnoreCase(playSong.songName)) return;
        //Reproduce la musica
        Preferences prefsMusic = Gdx.app.getPreferences("Musica");
        boolean playMusic = prefsMusic.getBoolean("play", true);
        if(playMusic) {
            playSong.music.play();
            currentlyPlaying = playSong;
        }

    }
    public static void stopMusic()
    {
        if(currentlyPlaying!=null)
        {
            currentlyPlaying.music.stop();
            currentlyPlaying.music.dispose();
            currentlyPlaying = null;
        }
    }
    public static void restartMusic()
    {
        if(currentlyPlaying!=null)
        {
            currentlyPlaying.music.stop();
            currentlyPlaying.music.play();
        }
    }

}
