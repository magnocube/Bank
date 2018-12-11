package client;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by stefa on 3/28/2017.
 */
public class sound extends Thread{
    private String Path = null;
    public sound(String p){
        Path = p;
    }
    Clip clip =null;
    public void run(){
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(Path)));
            clip.start();
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);
            clip.close();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
}