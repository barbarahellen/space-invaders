import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;


public class PlanoDeFundo {

    private BufferedImage imagem;
    private int y;

    public PlanoDeFundo() {
        try {
            imagem = ImageIO.read(new File("imagens/espaco2.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar a imagem de fundo");
            e.printStackTrace();
        }
        y = 0;
    }

    public void desenhar(Graphics2D g) {
        g.drawImage(imagem, 0, y-600*2, imagem.getWidth(), imagem.getHeight(), null);
        g.drawImage(imagem, 0, y, imagem.getWidth(), -imagem.getHeight(), null);
        g.drawImage(imagem, 0, y, imagem.getWidth(), imagem.getHeight(), null);
        y+=3;

        if (y >= 600*2) {
            y = 0;
        }
    }
}