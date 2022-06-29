import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 

public class Nave {
    
    private BufferedImage desenho;
    private int x;
    private int velocidade;
    private boolean podeDisparar;
    private boolean podeDispararTirao;
    private int tempo;

    public Nave(){
        try{
            desenho = ImageIO.read(new File("imagens/batnave.png"));
        } catch(IOException e){
            System.out.println("Erro no carregamento da nave");
            e.printStackTrace();
        }

        x = 350;
        velocidade = 3;
        podeDisparar = true;
        tempo = 0;
        podeDispararTirao = true;
        
    }
    public void desenhar(Graphics2D grafico){
        //escalar a nave
        grafico.drawImage(desenho, x, 500, x + 50, 550, 0, 0, desenho.getWidth(), desenho.getHeight(), null);
    
    }

    public Tiro disparar() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        podeDisparar = false;
        tempo = 0;

        Tiro novoTiro = new Tiro(x + 24, 482,10);
        return novoTiro;
         
    }

    public Tirao dispararTirao()throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        podeDispararTirao = false;
        tempo = 0;

        Tirao novoTirao = new Tirao(x + 24, 482, 3);
        return novoTirao;
    }

    public void mover(int valor){
        if(valor == 1){
            x += velocidade;
        }else if (valor == -1){
            x -= velocidade;
        }

        if(tempo >= 10){
            podeDisparar = true;
            tempo = 0;
        }
        tempo++;
    }

    public boolean podeDisparar(){
        //podeDisparar = true;
        return podeDisparar;
        
    }

    public boolean podeDispararTirao(){
        //podeDisparar = true;
        return podeDispararTirao;
        
    }

    public void ficarTela(){
        if (x > 735){
            x -= velocidade;
        }
        if (x < 0){
            x = 0;
        }
    }
    
    public int getX(){
        return x;
    }
}
