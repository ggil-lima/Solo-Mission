package org.academiadecodigo.spaceinvaders.gameplay;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    public Clip clip;
    public URL[] soundURL = new URL[20];
    public float currentVolume = 0f;
    public FloatControl fc;
    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/TIE_Shot.wav");
        soundURL[1] = getClass().getResource("/Sounds/Proton_Torpedo.wav");
        soundURL[2] = getClass().getResource("/Sounds/MF_Shot.wav");
        soundURL[3] = getClass().getResource("/Sounds/Explosion.wav");
        soundURL[4] = getClass().getResource("/Sounds/Boss_Music.wav");
        soundURL[5] = getClass().getResource("/Sounds/Victory_Music.wav");
        soundURL[6] = getClass().getResource("/Sounds/Vader_Laser.wav");
        soundURL[7] = getClass().getResource("/Sounds/Deflect.wav");
        soundURL[8] = getClass().getResource("/Sounds/Vader_Laser_Flurry.wav");
        soundURL[9] = getClass().getResource("/Sounds/Torpedo_Explosion.wav");
        soundURL[10] = getClass().getResource("/Sounds/Overheat.wav");
        soundURL[11] = getClass().getResource("/Sounds/Alarm_Pulse.wav");
        soundURL[12] = getClass().getResource("/Sounds/Final_Boss.wav");
        soundURL[13] = getClass().getResource("/Sounds/Torpedoes_Online.wav");
        soundURL[14] = getClass().getResource("/Sounds/Torpedoes_Offline.wav");
        soundURL[15] = getClass().getResource("/Sounds/No_Torpedo.wav");


    }

    public void setFile (int i) {
        try {
            AudioInputStream as = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(as);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();
    }
    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop () {
        clip.stop();
    }
}
