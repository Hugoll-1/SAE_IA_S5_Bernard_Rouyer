package partie2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class knn extends AlgoClassification {

    private int k;
    private double maxDistance;
    private Imagette imgAEnlever;

    public knn(Donnees donneesEntrainement, int k) {
        super(donneesEntrainement);
        this.k = k;
    }

    @Override
    public int predireEtiquette(Imagette imagette) {

        // Liste qui associe une Imagette a une distance
        Map<Imagette, Integer> kPlusProches = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Imagette img = donnees.imagettes[i];
            int distance = calculerDistance(imagette, img);
            kPlusProches.put(img, distance);
        }

        rechercherPlusGrande(kPlusProches);

        // Pour chaque imagette des donnes
        for (int i = k; i < donnees.imagettes.length; i++) {

            Imagette img = donnees.imagettes[i];
            //Recherche de la distance
            int distance = calculerDistance(imagette, img);

            // Si on n'a pas encore dépassé k, on l'ajoute dans la liste
            //Possibilité d'inséré les valeurs au préalable
//            if (kPlusProches.size() < k) {
//                kPlusProches.put(img, distance);
//            } else {
            // Sinon
            // Recherche de la distance la plus grande dans la liste
//                int maxDistance = -1;
//                Imagette imgAEnlever = null;

//                for (Map.Entry<Imagette, Integer> entry : kPlusProches.entrySet()) {
//                    if (entry.getValue() > maxDistance) {
//                        maxDistance = entry.getValue();
//                        imgAEnlever = entry.getKey();
//                    }
//                }

            // Si la nouvelle image a une distance plus petite que la plus grande --> remplacement
            if (distance < maxDistance) {
                kPlusProches.remove(imgAEnlever);
                kPlusProches.put(img, distance);
                rechercherPlusGrande(kPlusProches);
            }
        }
//        }

        //On compte chaque occurences
        Map<Integer, Integer> occurrences = new HashMap<>();
        for (Imagette proche : kPlusProches.keySet()) {
            int etiquette = proche.etiquette;
            occurrences.put(etiquette, occurrences.getOrDefault(etiquette, 0) + 1);
        }

        //On prend la plus fréquente
        int etiquettePlusFrequente = -1;
        int maxOccurrences = 0;
        for (Map.Entry<Integer, Integer> entry : occurrences.entrySet()) {
            if (entry.getValue() > maxOccurrences) {
                maxOccurrences = entry.getValue();
                etiquettePlusFrequente = entry.getKey();
            }
        }
        return etiquettePlusFrequente;
    }

    private int calculerDistance(Imagette imagette1, Imagette imagette2) {
        //initialisation distance
        int distance = 0;

        int tailleLignes = imagette1.niveauxGris.length;
        int tailleCols = imagette1.niveauxGris[0].length;

        //parcours de l'ensemble des niveaux de gris
        for (int ligne = 0; ligne < tailleLignes; ligne++) {
            for (int col = 0; col < tailleCols; col++) {
                //comparaison entre chaque
                int diff = imagette1.niveauxGris[ligne][col] - imagette2.niveauxGris[ligne][col];
                //ajout dans variable distance (pas oublier valeurs négatives)
                distance += Math.abs(diff);
            }

        }
        return distance;
    }

    private void rechercherPlusGrande(Map<Imagette, Integer> kPlusProches) {
        maxDistance = -1;
        for (Map.Entry<Imagette, Integer> entry : kPlusProches.entrySet()) {
            if (entry.getValue() > maxDistance) {
                maxDistance = entry.getValue();
                imgAEnlever = entry.getKey();
            }
        }
//        for (Imagette imagette : kPlusProches.keySet()) {
//            double distance = kPlusProches.get(imagette);
//            if (kPlusProches.get(imagette) > maxDistance) {
//                maxDistance = distance;
//                imgAEnlever = imagette;
//            }
//        }
    }

    public static void main(String[] args) throws IOException {
        Donnees donnees = new Donnees();
        donnees.chargerDonnee("entrainement", 1000);
        knn plusProche = new knn(donnees, 10);

        System.out.println(plusProche.predireEtiquette(donnees.imagettes[3]));
        System.out.println(donnees.imagettes[3].etiquette);
    }
}
