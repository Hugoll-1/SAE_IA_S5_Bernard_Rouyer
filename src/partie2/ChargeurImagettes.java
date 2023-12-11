package partie2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ChargeurImagettes {
    public static Imagette[] chargerImages(String nomFichier, List<Integer> listeEtiquettes, int maxVal) throws IOException {
        //Ouverture du fichier
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./img/"+nomFichier));

//        lire num magique
        int numMagique = dataInputStream.readInt();
        if (numMagique != 2051) {
            throw new Error("Type incorrecte");
        }
//      lire nb images
        int nbImages = dataInputStream.readInt();
        if(maxVal != -1 && maxVal< nbImages){
            nbImages = maxVal;
        }
//      lire colonnes
        int cols = dataInputStream.readInt();
//      lire ligne
        int lignes = dataInputStream.readInt();

        //Création tableau imagettes
        Imagette[] imagettes = new Imagette[nbImages];

        //Pour chaque imagettes
        for (int i = 0; i < nbImages; i++) {
            if(i%(nbImages/10) == 0){
                double pourcentageAvancement = ( (double) i) / (nbImages - 1) * 100.0;
                System.out.println("Avancement : " + Math.floor(pourcentageAvancement) + "%");
            }
            //Création
            Imagette imagette = new Imagette(lignes,cols);
            //Pour chaque indice
            for (int ligne = 0; ligne < lignes; ligne++) {
                for (int col = 0; col < cols; col++) {
                    //Lire octet
                    int octet = dataInputStream.readUnsignedByte();
                    //ajout des données
                    imagette.niveauxGris[col][ligne] = octet;

                }
            }
            imagette.etiquette = listeEtiquettes.get(i);
            //Ajout de l'imagette dans tableau
            imagettes[i]=imagette;
        }
        //Fermer flux
        dataInputStream.close();
        //Retourner imagettes
        return imagettes;

    }

//    public static void main(String[] args) throws IOException {
//        Imagette[] imagettes = chargerImages("t10k-images.idx3-ubyte");
//        Imagette.sauverImage(imagettes[0].niveauxGris,0);
//        Imagette.sauverImage(imagettes[9].niveauxGris,9);
//    }


}
