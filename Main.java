import javax.swing.JFrame;
public class Main{
    public static void main(String[] args){
        JFrame janela = new JFrame("Space Invaders");
        janela.setSize(800, 600);
        janela.setLayout(null);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        
        Game spaceInvaders = new Game();
        spaceInvaders.setBounds(0, 0, 800, 600);
     
        janela.add(spaceInvaders);
        janela.setVisible(true);
        janela.addKeyListener(spaceInvaders);
    }
}
