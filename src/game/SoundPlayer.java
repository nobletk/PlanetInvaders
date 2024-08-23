package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer implements Runnable {
    private File file;
    private boolean loop, playing;
    private float volume;
    private int delay;


    public SoundPlayer(String filePath) {
        this.file = new File(filePath);
        this.loop = false;
        this.playing = true;
        this.delay = 0;
    }

    @Override
    public void run() {
        do {
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
                AudioFormat format = audioInputStream.getFormat();

                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

                line.open(format);
                line.start();

                FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(volume);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }
                line.drain();
                line.close();

                if (loop) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } while (loop && playing);
    }

    public void play() {
        new Thread(this).start();
    }

    public void setVolume(float v) {
        this.volume = v;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setDelay(int delayMs) {
        this.delay = delayMs;
    }

    public void stop() {
       playing = false;
       loop = false;
    }
}
