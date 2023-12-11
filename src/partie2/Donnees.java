package partie2;

import java.io.IOException;
import java.util.List;

public class Donnees {
    public Imagette[] imagettes;

    public void chargerDonnee(String type, int maxVal) throws IOException {
        String fichierImage;
        String fichierEtiquettes;
        if(type == "entrainement") {
            fichierImage = "train-images.idx3-ubyte";
            fichierEtiquettes = "train-labels.idx1-ubyte";
        }else {
            fichierImage = "t10k-images.idx3-ubyte";
            fichierEtiquettes = "t10k-labels.idx1-ubyte";
        }
        System.out.println("------------------------------");
        System.out.println("Chargement des Etiquettes");
        List<Integer> listeEtiquettes =  ChargeurEtiquettes.chargerEtiquettes(fichierEtiquettes, maxVal);
        System.out.println("Etiquettes chargées: "+listeEtiquettes.size());

        System.out.println("------------------------------");
        System.out.println("Chargement des images");
        this.imagettes = ChargeurImagettes.chargerImages(fichierImage, listeEtiquettes, maxVal);
        System.out.println("Imagettes chargées: "+imagettes.length);

    }

//    public static void main(String[] args) throws IOException {
//        Donnees donnee = new Donnees();
//        donnee.chargerDonnee();
//
//    }

}
