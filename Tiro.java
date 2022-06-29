import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Tiro {

    protected int x; 
    protected int y;
    protected int velocidade;
    protected int tamX;
    protected int tamY;


    public Tiro(int inicioX, int inicioY,int velocidade) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.x = inicioX;
        this.y = inicioY;
        this.velocidade = velocidade;
        this.tamX = 3;
        this.tamY = 15;
        new Som("sons\\tirinho.wav").play();
        
    }
    
    public void desenhar(Graphics2D g){
        g.setColor(Color.red);
        g.fillRect(x, y, tamX, tamY);
    }

    public void atualizar(){
        y -= velocidade;
    }

    public boolean destroi(){
        return y < 0;
    }
    public boolean colisao(Alien alien){
        if (x>= alien.getX() && x + tamX <= alien.getX() + alien.getTam()){
            if (y <= alien.getY() + alien.getTam()){
                return true;
            }
        }
        return false;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
