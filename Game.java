import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Color;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.File;
import javax.swing.JPanel;
//import java.awt.image.BufferedImage;



public class Game extends JPanel implements Runnable, KeyListener{
    
    private int direcao = 0;
    private Nave nave;
    private ArrayList<Tiro> tiros;
    private ArrayList<Tirao> tiroes;
    private ArrayList<Alien> aliens;
    private PlanoDeFundo fundo;
    private boolean venceu;
    private boolean perdeu;
    //private BufferedImage imagem;
    private Font letrinha = new Font("Consolas",Font.BOLD,20);


    public Game(){

        nave = new Nave();
        tiros = new ArrayList<Tiro>();
        aliens = new ArrayList<Alien>();
        fundo = new PlanoDeFundo();
        tiroes = new ArrayList<Tirao>();
        venceu = false;
        perdeu = false;
        BufferedImage imagemAlien = null;

        try {
            imagemAlien = ImageIO.read(new File("imagens/alien2.png"));
        } catch (IOException e) {
            System.out.println("Erro no carregament do Alien");
            e.printStackTrace();
        }
        
        for (int i =0; i < 50; i++){
            aliens.add(new Alien(imagemAlien, 20 + i % 10 *50, 20 + i/10 * 50, 1));
        }

        Thread lacoDoJogo = new Thread(this); //this = proprio game
        lacoDoJogo.start();
    }

    @Override
    //o jogo tem que ser uma thread separada para não travar a janela
    public void run(){
        while(true){
            long tempoInicial = System.currentTimeMillis();
            
            update();
            repaint();

            long tempoFinal = System.currentTimeMillis();
            long diferenca = 16 - (tempoFinal - tempoInicial);

            if(diferenca > 0) {
                soneca(diferenca);
            }
        }
    }

    private void update(){
        if (aliens.size() == 0){
            venceu = true;
        }
        nave.mover(direcao);
        nave.ficarTela();

        for(int i =0; i < aliens.size(); i++){
            aliens.get(i).atualizar();

            if(aliens.get(i). getY() >= 483 ){
                perdeu = true;
            }
        }

        for (int i = 0; i< tiros.size(); i++){
            tiros.get(i).atualizar();
            if (tiros.get(i).destroi()){
                tiros.remove(i);
                i--;
            } else{
                for(int j = 0; j < aliens.size(); j++){
                    if(tiros.get(i).colisao(aliens.get(j))) {
                        aliens.remove(j);   
                        j--;
                        tiros.remove(i);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < tiroes.size(); i++){
            tiroes.get(i).atualizar();

            if (tiroes.get(i).destroi()){
                tiroes.remove(i);
                i--;
            } else{
                for(int j = 0; j < aliens.size(); j++){
                    if(tiroes.get(i).colisao(aliens.get(j))) {
                        aliens.remove(j);   
                        j--;
                        tiroes.remove(i);
                        break;
                    }
                }
            }
        }

        for(int i = 0; i < aliens.size(); i++) {
            if(aliens.get(i).getX() <= 0 || aliens.get(i).getX() >= 800-50) {
                for(int j = 0; j < aliens.size(); j++) {
                    aliens.get(j).trocaDirecao();
                }
                break;
            }
        }
    }

    int x = 0;

    public void paintComponent(Graphics g2){//do JPanel
        super.paintComponent(g2); //limpar a tela

        Graphics2D g = (Graphics2D) g2.create();
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        //desenha os aliens
        fundo.desenhar(g);

        //desenha a nave
        for(int i =0; i < aliens.size(); i++){
            aliens.get(i).desenhar(g);
        }

        //desenha os tiros
        nave.desenhar(g);
        for (int i = 0; i < tiros.size(); i++){
            tiros.get(i).desenhar(g);
        }
        for (int i = 0; i <tiroes.size(); i++){
            tiroes.get(i).desenhar(g);
        }

        // mensagem final do jogo
        if (venceu){
            g.setColor(Color.PINK);
            g.setFont(letrinha);
            g.drawString("YOU WON", 350, 260);

            /* 
            try{
                imagem = ImageIO.read(new File("imagens/ganhouu.png"));
            } catch(IOException e){
                System.out.println("Erro no carregamento da nave");
                e.printStackTrace();
            }
            g2.drawImage(imagem, 250, 150, 300, 300, null); */ 
        }

        if (perdeu){
            g.setColor(Color.PINK);
            g.setFont(letrinha);
            g.drawString("LOSER", 350, 260);

            /* 
            try{
                imagem = ImageIO.read(new File("imagens/perdeu.png"));
            } catch(IOException e){
                System.out.println("Erro no carregamento da imagem");
                e.printStackTrace();
            }
            g2.drawImage(imagem, 250, 150, 300, 300, null); */
        }
    }

    private void soneca(long duracao){
        try{
            Thread.sleep(duracao);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // ao apertar as teclas A e D a nave se move para os lados
        if (e.getKeyCode() == KeyEvent.VK_D){
            direcao = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            direcao = -1;
        }

        //ao apertar a tecla espaço, a nave atira
        if (e.getKeyCode() == KeyEvent.VK_SPACE && nave.podeDisparar()){
            try {
                tiros.add(nave.disparar());
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e1) {
                e1.printStackTrace();
            }
        }
        // ao apertar a tecla v, a nave atira um tiro maior 
        if (e.getKeyCode() == KeyEvent.VK_V && nave.podeDispararTirao()){
            try{
                tiros.add(nave.dispararTirao());
            }catch (UnsupportedAudioFileException | LineUnavailableException | IOException e1) {
                e1.printStackTrace();
            } 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D){
            direcao = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            direcao = 0;
        }
    }
}
