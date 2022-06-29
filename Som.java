import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Som{
    
    Clip clip;

    AudioInputStream audioStream;

    public Som(String thePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        audioStream = AudioSystem.getAudioInputStream(new File(thePath));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }

    public void play() {
        clip.start();
    }
}

