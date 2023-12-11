import lib.HyperbolicTangentFunction;
import lib.MLP;
import lib.SigmoidFunction;
import lib.TransferFunction;

import java.util.Arrays;

// Définir la classe principale du perceptron multi-couches
public class MainPerceptron {

    static double[][] ensembleExempleEntrainement = new double[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };

    // AND
    static double[][] ensembleSortieAND = new double[][]{
            {0},
            {0},
            {0},
            {1}
    };

    //OR
    static double[][] ensembleSortieOR = new double[][]{
            {0},
            {1},
            {1},
            {1}
    };

    //XOR
    static double[][] ensembleSortieXOR = new double[][]{
            {0},
            {1},
            {1},
            {0}
    };
    public static void main(String[] args) {
        // Configurer le MLP
        int maxEpochs = 10000;
        int nbTrainExemple = ensembleExempleEntrainement.length;
        int nbTestExemples = ensembleExempleEntrainement.length;
        double tauxApprentissage = 0.1;

        TransferFunction fonctionActivation = new HyperbolicTangentFunction(); // Choisir la fonction d'activation

        int[] couches = {2,3,1};
        MLP mlp = new MLP(couches, tauxApprentissage, fonctionActivation);

        // Entraînement du réseau
        for (int epoch = 0; epoch < maxEpochs; epoch++) {
            double erreurTotale = 0.0;
            double erreurMoyenne = 0.0;
            for (int exemple = 0; exemple < nbTrainExemple; exemple++) {
                // Obtenir l'exemple d'entraînement
                double[] entree = getTrainingInput(exemple);
                double[] sortieCible = getTargetOutput(exemple);

                // Exécuter le réseau et rétropropagation
                double erreur = mlp.backPropagate(entree, sortieCible);

                // Afficher l'erreur
                System.out.println("Epoch " + (epoch+1) + ", exemple " + (exemple+1) + ", erreur: " + erreur);

                // Calculer l'erreur totale
                erreurTotale += erreur;

                // Calculer l'erreur moyenne
                erreurMoyenne += erreur;


//                // Vérifier si l'objectif est atteint
//                if (erreur < targetError) {
//                    System.out.println("Objectif atteint. Arrêt de l'apprentissage.");
//                    break;
//                }
            }
            System.out.println("Erreur totale: " + erreurTotale);
            System.out.println("Erreur moyenne: " + erreurMoyenne/nbTrainExemple);
        }

        // Tester le réseau sur des exemples
        for (int exemple = 0; exemple < nbTestExemples; exemple++) {
            double[] entreeTest = getTestInput(exemple);
            double[] sortiePredite = mlp.execute(entreeTest);
            double sortieCible = getTargetOutput(exemple)[0];

            // Afficher les résultats de test
            System.out.println("Test exemple " + exemple + ": " + Arrays.toString(sortiePredite)+ " (cible: " + sortieCible + ")");
        }
    }



    // Méthode pour obtenir les données d'entraînement
    public static double[] getTrainingInput(int example) {
        return ensembleExempleEntrainement[example];
    }

    // Méthode pour obtenir les sorties attendues pour les données d'entraînement
    public static double[] getTargetOutput(int example) {
        return ensembleSortieXOR[example];
    }

    // Méthode pour obtenir les données de test
    public static double[] getTestInput(int example) {
        return ensembleExempleEntrainement[example];
    }

}