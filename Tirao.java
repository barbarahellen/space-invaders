import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Tirao extends Tiro{
    
    public Tirao(int inicioX, int inicioY, int velocidade) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(inicioX, inicioY, velocidade);
        this.x = inicioX;
        this.y = inicioY;
        velocidade = 3;
    }

    @Override
    public void desenhar(Graphics2D g){
        g.setColor(Color.pink);
        g.fillRect(x, y, 10, 15);
    }    
}
