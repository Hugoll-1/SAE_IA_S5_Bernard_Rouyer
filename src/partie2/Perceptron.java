package partie2;

import Partie1.HyperbolicTangentFunction;
import Partie1.MLP;
import Partie1.SigmoidFunction;
import Partie1.TransferFunction;

import java.util.ArrayList;
import java.util.Arrays;

public class Perceptron extends AlgoClassification {
    /**
     * Nombre d'itérations maximales
     */
    int maxEpochs;

    /**
     * Taux d'apprentissage
     */
    double tauxApprentissage;

    /**
     * Erreur cible
     */
    double erreurCible;

    /**
     * Couches du réseau
     */
    int[] couches;

    MLP mlp;

    public Perceptron(Donnees donneesEntrainement, int maxEpochs, double tauxApprentissage, int[] couches, double erreurCible, String algoChoisi) {
        super(donneesEntrainement);
        this.maxEpochs = maxEpochs;
        this.tauxApprentissage = tauxApprentissage;
        this.couches = couches;
        this.erreurCible = erreurCible;


        TransferFunction fonctionActivation = switch (algoChoisi) {
            case "Sigmoid" -> new SigmoidFunction();
            case "Tanh" -> new HyperbolicTangentFunction();
            default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
        };
        mlp = new MLP(couches, tauxApprentissage, fonctionActivation);

        // Entraînement du réseau
        for (int epoch = 0; epoch < maxEpochs; epoch++) {
//            double erreurTotale = 0.0;
//            double erreurMoyenne = 0.0;
//            double erreurMax = 0.0;
            for (int exemple = 0; exemple < this.donnees.imagettes.length; exemple++) {
                // Obtenir l'exemple d'entraînement
                double[] entree = getTrainingInput(exemple);
                double[] sortieCible = getTargetOutput(exemple);

                // Exécuter le réseau et rétropropagation
                double erreur = mlp.backPropagate(entree, sortieCible);

                // Afficher l'erreur
//                System.out.println("Epoch " + (epoch + 1) + ", exemple " + (exemple + 1) + ", erreur: " + erreur);

                // Calculer l'erreur totale
//                erreurTotale += erreur;

                // Calculer l'erreur moyenne
//                erreurMoyenne += erreur;

                // Calculer l'erreur max
//                if (erreur > erreurMax) {
//                    erreurMax = erreur;
//                }
            }
//            System.out.println("-------------------------------------------");
            if (epoch % (maxEpochs / 10.0) == 0) {
                double pourcentageAvancement = ((double) epoch) / (maxEpochs - 1) * 100.0;
                System.out.println("Avancement : " + Math.floor(pourcentageAvancement) + "%");
            }
//            System.out.println("Epoch " + (epoch + 1) + " terminée.");
//            System.out.println("Erreur totale: " + erreurTotale);
//            System.out.println("Erreur moyenne: " + erreurMoyenne / this.donnees.imagettes.length);
//            System.out.println("Erreur max: " + erreurMax);
            // Vérifier si l'objectif est atteint
//            if (erreurMax < erreurCible) {
//                System.out.println("-------------------------------------------");
//                System.out.println("Objectif atteint en " + (epoch + 1) + " epoch sur " + maxEpochs + ". Arrêt de l'apprentissage.");
//                break;
//            }

        }
    }

    @Override
    public int predireEtiquette(Imagette imagette) {
        //Affichage des résultats
        double[] res = mlp.execute(getNiveauxGris(imagette));
        int positionMax = 0;
        for (int i = 0; i < res.length; i++) {
            if (res[i] > res[positionMax]) {
                positionMax = i;
            }
        }
//        System.out.println("Imagette théorique : " + positionMax + " \tImagette réelle : " + imagette.etiquette);
        // Tester le réseau sur des exemples
        return positionMax;
    }


    // Méthode pour obtenir les données d'entraînement
    public double[] getTrainingInput(int example) {
        Imagette imagette = donnees.imagettes[example];
        return getNiveauxGris(imagette);
    }

    private static double[] getNiveauxGris(Imagette imagette) {
        int ligne = imagette.niveauxGris.length;
        int colone = imagette.niveauxGris[0].length;
        double[] entreeCibleArray = new double[ligne*colone];

        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colone; j++) {
                entreeCibleArray[i*ligne+j] = imagette.niveauxGris[i][j];
            }
        }
        return entreeCibleArray;
    }


    // Méthode pour obtenir les sorties attendues pour les données d'entraînement
    public double[] getTargetOutput(int example) {
        double[] tableau = new double[]{0,0,0,0,0,0,0,0,0,0};
        tableau[donnees.imagettes[example].etiquette] = 1;
        return tableau;
    }
}
