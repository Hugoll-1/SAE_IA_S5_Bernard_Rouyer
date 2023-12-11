import lib.HyperbolicTangentFunction;
import lib.MLP;
import lib.SigmoidFunction;
import lib.TransferFunction;

import java.util.Arrays;

// Définir la classe principale du perceptron multi-couches
public class MainPerceptron {

    // Définir les paramètres du perceptron
    /**
     * Nombre d'itérations maximales
     */
    static final int maxEpochs = 10000;

    /**
     * Taux d'apprentissage
     */
    static final double tauxApprentissage = 0.1;

    /**
     * Erreur cible
     */
    static final double erreurCible = 0.01;

    /**
     * Données choisies (AND, OR, XOR)
     */
    static final String donneesChoisies = "OR";

    /**
     * Algorithme choisi (Sigmoid, Tanh)
     */
    static final String algoChoisi = "Sigmoid";

    /**
     * Couches du réseau
     */
    static final int[] couches = {2,3,1};
    // fin des paramètres du perceptron


    static double[][] ensembleExempleEntrainement = switch (algoChoisi) {
        case "Sigmoid" -> new double[][]{
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        case "Tanh" -> new double[][]{
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1}
        };
        default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
    };

    static final int nbTrainExemple = ensembleExempleEntrainement.length;
    static final int nbTestExemples = ensembleExempleEntrainement.length;


    public static void main(String[] args) {
        // Configurer le MLP


        TransferFunction fonctionActivation = switch (algoChoisi) {
            case "Sigmoid" -> new SigmoidFunction();
            case "Tanh" -> new HyperbolicTangentFunction();
            default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
        };

        MLP mlp = new MLP(couches, tauxApprentissage, fonctionActivation);

        // Entraînement du réseau
        for (int epoch = 0; epoch < maxEpochs; epoch++) {
            double erreurTotale = 0.0;
            double erreurMoyenne = 0.0;
            double erreurMax = 0.0;
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

                // Calculer l'erreur max
                if (erreur > erreurMax) {
                    erreurMax = erreur;
                }
            }
            System.out.println("Erreur totale: " + erreurTotale);
            System.out.println("Erreur moyenne: " + erreurMoyenne/nbTrainExemple);
            System.out.println("Erreur max: " + erreurMax);
            // Vérifier si l'objectif est atteint
            if(erreurMax < erreurCible) {
                System.out.println("-------------------------------------------");
                System.out.println("Objectif atteint en "+(epoch+1)+" epoch sur "+maxEpochs+". Arrêt de l'apprentissage.");
                break;
            }

        }

        System.out.println("Données choisies: " + donneesChoisies);
        // Tester le réseau sur des exemples
        for (int exemple = 0; exemple < nbTestExemples; exemple++) {
            double[] entreeTest = getTestInput(exemple);
            double[] sortiePredite = mlp.execute(entreeTest);
            double sortieCible = getTargetOutput(exemple)[0];

            // Afficher les résultats de test
            System.out.println("Test exemple " + exemple + ": " + Arrays.toString(sortiePredite)+ " (cible: " + sortieCible + "), erreur: " + Math.abs(sortieCible - sortiePredite[0]));
        }
    }



    // Méthode pour obtenir les données d'entraînement
    public static double[] getTrainingInput(int example) {
        return ensembleExempleEntrainement[example];
    }

    // AND
    static double[][] ensembleSortieAND = switch (algoChoisi) {
        case "Sigmoid" -> new double[][]{
                {0},
                {0},
                {0},
                {1}
        };
        case "Tanh" -> new double[][]{
                {-1},
                {-1},
                {-1},
                {1}
        };
        default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
    };

    //OR
    static double[][] ensembleSortieOR = switch (algoChoisi) {
        case "Sigmoid" -> new double[][]{
                {0},
                {1},
                {1},
                {1}
        };
        case "Tanh" -> new double[][]{
                {-1},
                {1},
                {1},
                {1}
        };
        default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
    };

    //XOR
    static double[][] ensembleSortieXOR = switch (algoChoisi) {
        case "Sigmoid" -> new double[][]{
                {0},
                {1},
                {1},
                {0}
        };
        case "Tanh" -> new double[][]{
                {-1},
                {1},
                {1},
                {-1}
        };
        default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
    };

    // Méthode pour obtenir les sorties attendues pour les données d'entraînement
    public static double[] getTargetOutput(int example) {
        return switch (donneesChoisies) {
            case "AND" -> ensembleSortieAND[example];
            case "OR" -> ensembleSortieOR[example];
            case "XOR" -> ensembleSortieXOR[example];
            default -> throw new IllegalStateException("Unexpected value: " + donneesChoisies);
        };
    }

    // Méthode pour obtenir les données de test
    public static double[] getTestInput(int example) {
        return ensembleExempleEntrainement[example];
    }

}