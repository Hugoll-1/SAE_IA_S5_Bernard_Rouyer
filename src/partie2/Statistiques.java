package partie2;

import java.io.IOException;

public class Statistiques {
    private AlgoClassification algorithme;
    private Donnees donneesTest;

    public Statistiques(AlgoClassification algorithme, Donnees donneesTest) {
        this.algorithme = algorithme;
        this.donneesTest = donneesTest;
    }

    public void evaluer() {
        int totalEchantillons = donneesTest.imagettes.length;
        int correctes = 0;

        System.out.println("------------------------------");
        System.out.println("Debut de l'évaluation");
        //Parcours des imagettes
        //Calculs sur chaque imagettes
        //Verif
        for (int i = 0; i <= donneesTest.imagettes.length - 1; i++) {
            Imagette imagette = donneesTest.imagettes[i];

            if (i % (totalEchantillons / 10.0) == 0) {
                double pourcentageAvancement = ((double) i) / (totalEchantillons - 1) * 100.0;
                System.out.println("Avancement : " + Math.floor(pourcentageAvancement) + "%");
            }

            int etiquetteReelle = imagette.etiquette;
            int etiquettePredite = algorithme.predireEtiquette(imagette);


            if (etiquetteReelle == etiquettePredite) {
                correctes++;
            }
        }

        double pourcentCorrecte = (double) correctes / totalEchantillons * 100.0;
        System.out.println("Pourcentage de test correctes : " + pourcentCorrecte + "%");
        System.out.println("Nombre de test corrects : " + correctes + "/" + totalEchantillons);
    }

    public double[] bonnesReponsesParClasses() {
        System.out.println("------------------------------");
        System.out.println("Debut de l'évaluation");

        int totalEchantillons = donneesTest.imagettes.length;
        int correctes = 0;

        // 10 classes de chiffres de 0 à 9
        int[] occurrencesParEtiquettes = new int[10];
        // Int[] pour compter les bonnes réponses par classe
        int[] bonnesReponsesParEtiquettes = new int[10];

        //Parcours des imagettes
        //Calculs sur chaque imagettes
        //Verif
        for (int i = 0; i <= donneesTest.imagettes.length - 1; i++) {

            Imagette imagette = donneesTest.imagettes[i];

            int etiquetteReelle = imagette.etiquette;
            int etiquettePredite = algorithme.predireEtiquette(imagette);

            if (i % (totalEchantillons / 10.0) == 0) {
                double pourcentageAvancement = ((double) i) / (totalEchantillons - 1) * 100.0;
                System.out.println("Avancement : " + Math.floor(pourcentageAvancement) + "%");
            }

            // Incrémentation du compteur de la classe réelle
            occurrencesParEtiquettes[etiquetteReelle]++;

            if (etiquetteReelle == etiquettePredite) {
                // Incrémentation du compteur des bonnes réponses pour la classe
                bonnesReponsesParEtiquettes[etiquetteReelle]++;
                correctes++;
            }
        }


        // Tableau pour stocker les pourcentages
        double[] pourcentagesParClasse = new double[10];

        //Insertion des valeurs pour chaque classes (0-9)
        for (int etiquette = 0; etiquette < 10; etiquette++) {
            //attention au division par 0
            if (occurrencesParEtiquettes[etiquette] == 0) {
                pourcentagesParClasse[etiquette] = 0.0;
            } else {
                pourcentagesParClasse[etiquette] = (double) bonnesReponsesParEtiquettes[etiquette] / occurrencesParEtiquettes[etiquette] * 100.0;
            }
        }
        double pourcentCorrecte = (double) correctes / totalEchantillons * 100.0;
        System.out.println("Pourcentage de test correctes : " + pourcentCorrecte + "%");
        System.out.println("Nombre de test corrects : " + correctes + "/" + totalEchantillons);

        return pourcentagesParClasse;

    }

    // Paramétrage
    /**
     * Nombre d'imagettes d'entrainement à charger
     */
    static final int nbImagettesEntrainement = 1000;

    /**
     * Nombre d'imagettes de test à charger
     */
    static final int nbImagettesTest = 1000;

    /**
     * Algorithme choisi (PlusProche, knn, Perceptron)
     */
    static final String algoChoisi = "Perceptron";

    // Paramètre pour knn
    /**
     * Paramètre k
     */
    static final int k = 5;

    // Paramètre pour Perceptron
    /**
     * Nombre d'itérations maximales
     */
    static final int maxEpochs = 40;

    /**
     * Taux d'apprentissage
     */
    static final double tauxApprentissage = 0.01;

    /**
     * Couches du réseau
     */
    static final int[] couches = {16 * 16, 16 * 16, 10};

    /**
     * Erreur cible
     */
    static final double erreurCible = 0.01;

    public static void main(String[] args) throws IOException {
        // Début chronomètre total
        long debut = System.currentTimeMillis();

        System.out.println("-------------------------------------------");
        // Chargement des données d'entrainement
        System.out.println("Chargement des données d'entrainement en cours");
        Donnees donnees = new Donnees();
        donnees.chargerDonnee("entrainement", nbImagettesEntrainement);
        // Fin mesure du temps de chargement donnes entrainement
        long finChargementDonnees = System.currentTimeMillis();
        System.out.println("Temps de chargement des données entrainement : " + (finChargementDonnees - debut) + " ms");


        System.out.println("-------------------------------------------");
        System.out.println("Début de l'entrainement");

        //knn 10 000 k:20: 92%; k:10: 96%; k:5: %; k:1: 92% || 1 000 k:20: 99%; k:10: %; k:5: 99%; k:1: 82%
        AlgoClassification algo = switch (algoChoisi) {
            case "PlusProche" -> new PlusProche(donnees);
            case "knn" -> new knn(donnees, k);
            case "Perceptron" -> new Perceptron(donnees, maxEpochs, tauxApprentissage, couches, erreurCible);
            default -> throw new IllegalStateException("Unexpected value: " + algoChoisi);
        };

        long finEntrainement = System.currentTimeMillis();
        System.out.println("Temps d'entrainement : " + (finEntrainement - finChargementDonnees) + " ms");

        System.out.println("-------------------------------------------");
        System.out.println("Chargement des données de test en cours");
        Donnees donneesTest = new Donnees();
        donneesTest.chargerDonnee("test", nbImagettesTest);
        long finChargementDonneesTest = System.currentTimeMillis();
        System.out.println("Temps de chargement des données test : " + (finChargementDonneesTest - finEntrainement) + " ms");

        Statistiques statistiques = new Statistiques(algo, donneesTest);

//        statistiques.evaluer();
        double[] pourcentages = statistiques.bonnesReponsesParClasses();
        long finEvaluation = System.currentTimeMillis();
        System.out.println("Temps d'évaluation : " + (finEvaluation - finChargementDonneesTest) + " ms");

        for (int classe = 0; classe < 10; classe++) {
            System.out.println("Pourcentage de classe " + classe + " correctes : " + pourcentages[classe] + "%");
        }
        //Fin chronomètre total
        long fin = System.currentTimeMillis();
        System.out.println("Temps d'exécution total: " + (fin - debut) + " ms");
    }
}
/*
Pourcentage de test correctes : 95.53%
Nombre de test corrects : 9553/10000
Pourcentage de classe 0 correctes : 98.87755102040816%
Pourcentage de classe 1 correctes : 99.55947136563876%
Pourcentage de classe 2 correctes : 92.05426356589147%
Pourcentage de classe 3 correctes : 96.93069306930693%
Pourcentage de classe 4 correctes : 94.29735234215886%
Pourcentage de classe 5 correctes : 95.4035874439462%
Pourcentage de classe 6 correctes : 98.01670146137788%
Pourcentage de classe 7 correctes : 94.64980544747081%
Pourcentage de classe 8 correctes : 90.34907597535934%
Pourcentage de classe 9 correctes : 94.74727452923688%

 */