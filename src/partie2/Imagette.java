package partie2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imagette {
    public int[][] niveauxGris;
    public int etiquette;

    public Imagette(int lignes,int cols){
        niveauxGris = new int[lignes][cols];
    }

    public static void sauverImage(int[][] tabImage, int numImg) throws IOException {
        //Récupération de la taille de l'img
        int cols = tabImage.length;
        int lignes = tabImage[0].length;

        //Création du buffered
        BufferedImage image = new BufferedImage(cols, lignes, BufferedImage.TYPE_3BYTE_BGR);

        //Parcourt du tabImage
        for (int ligne = 0; ligne < lignes; ligne++) {
            for (int col = 0; col < cols; col++) {
//                int pixelColor = tabImage[ligne][col] *(256*256) + tabImage[ligne][col]*256+ tabImage[ligne][col];
                int pixelColor = tabImage[ligne][col] <<16 | tabImage[ligne][col]<<8 | tabImage[ligne][col];

//                int pixelColor = tabImage[ligne][col];
//                image.setRGB(ligne,col,new Color(pixelColor,pixelColor,pixelColor).getRGB());
                image.setRGB(ligne,col,pixelColor);
            }
        }

        //Création des images
        File outputImageFile = new File("./images/"+numImg+".png");
        ImageIO.write(image, "png", outputImageFile);

        //Sauvegarde de l'image


    }
}
