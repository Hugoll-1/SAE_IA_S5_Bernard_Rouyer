package partie2;

import Partie1.MLP;
import Partie1.SigmoidFunction;
import Partie1.TransferFunction;

import java.util.Arrays;

public class Perceptron extends AlgoClassification {
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
     * Couches du réseau
     */
    static final int[] couches = {2, 3, 1};

    MLP mlp;

    public Perceptron(Donnees donneesEntrainement) {
        super(donneesEntrainement);
        TransferFunction fonctionActivation = new SigmoidFunction();
        mlp = new MLP(couches, tauxApprentissage, fonctionActivation);

        // Entraînement du réseau
        for (int epoch = 0; epoch < maxEpochs; epoch++) {
            double erreurTotale = 0.0;
            double erreurMoyenne = 0.0;
            double erreurMax = 0.0;
            for (int exemple = 0; exemple < this.donnees.imagettes.length; exemple++) {
                // Obtenir l'exemple d'entraînement
                Imagette entree = getTrainingInput(exemple);
                int sortieCible = getTargetOutput(exemple);

                // Exécuter le réseau et rétropropagation
                double erreur = mlp.backPropagate(entree, sortieCible);

                // Afficher l'erreur
                System.out.println("Epoch " + (epoch + 1) + ", exemple " + (exemple + 1) + ", erreur: " + erreur);

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
            System.out.println("Erreur moyenne: " + erreurMoyenne / this.donnees.imagettes.length);
            System.out.println("Erreur max: " + erreurMax);
            // Vérifier si l'objectif est atteint
            if (erreurMax < erreurCible) {
                System.out.println("-------------------------------------------");
                System.out.println("Objectif atteint en " + (epoch + 1) + " epoch sur " + maxEpochs + ". Arrêt de l'apprentissage.");
                break;
            }

        }
    }

    @Override
    public int predireEtiquette(Imagette imagette) {
        // Tester le réseau sur des exemples
        int sortiePredite = mlp.execute(imagette);

        return sortiePredite;
    }


    // Méthode pour obtenir les données d'entraînement
    public Imagette getTrainingInput(int example) {
        return this.donnees.imagettes[example];
    }


    // Méthode pour obtenir les sorties attendues pour les données d'entraînement
    public int getTargetOutput(int example) {
        return this.donnees.imagettes[example].etiquette;
    }

}
