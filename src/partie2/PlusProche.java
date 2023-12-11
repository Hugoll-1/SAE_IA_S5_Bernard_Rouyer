package partie2;

import java.io.IOException;

public class PlusProche extends AlgoClassification {

    public PlusProche(Donnees donneesEntrainement) {
        super(donneesEntrainement);
    }

    @Override
    public int predireEtiquette(Imagette imagette) {
        Imagette[] imagettes = donnees.imagettes;
        //Création index du plus proche + initialisation
        //Idem avec ditance du plus proche
        int indexPlusProche = 0;
        int distancePlusProche = calculerDistance(imagette, imagettes[indexPlusProche]);

        //parcours de l'ensemble des imagettes
        for (int i = 1; i<imagettes.length;i++) {
            Imagette img = imagettes[i];
            //Calcul de la distance entre les 2
            int distance = calculerDistance(imagette, img);
            //Comparaison
            if(distancePlusProche>distance){
                //Si plus proche --> changement index
                distancePlusProche = distance;
                indexPlusProche = i;
            }
        }
        return imagettes[indexPlusProche].etiquette;

    }

    private int calculerDistance(Imagette imagette1, Imagette imagette2){
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

    public static void main(String[] args) throws IOException {
        Donnees donnees = new Donnees();
        donnees.chargerDonnee("entrainement",1000);
        PlusProche plusProche = new PlusProche(donnees);

        System.out.println(plusProche.predireEtiquette(donnees.imagettes[3]));
        System.out.println(donnees.imagettes[3].etiquette);
    }
}
