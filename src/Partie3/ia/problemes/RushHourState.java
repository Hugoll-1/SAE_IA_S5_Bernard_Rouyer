package Partie3.ia.problemes;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.Misc;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.recherche.HasHeuristic;

import java.util.Arrays;

import static Partie3.ia.problemes.RushHour.LEFT;
import static Partie3.ia.problemes.RushHour.RIGHT;
import static Partie3.ia.problemes.RushHour.UP;
import static Partie3.ia.problemes.RushHour.DOWN;

public class RushHourState extends State implements HasHeuristic {

    private int difficulte = 2;

    // Représentation des véhicules
    private int[][] carte;

    private int[] coorDonneesSortie = new int[]{2, 5};

    private int[] listeNumVoiture;

    public RushHourState() {
        this.carte = switch (difficulte) {
            case 1 -> new int[][]{
                    {0, 0, 2, 0, 3, 3},
                    {0, 0, 2, 0, 0, 0},
                    {1, 1, 2, 0, 0, 0},
                    {4, 4, 4, 0, 0, 5},
                    {0, 0, 0, 0, 0, 5},
                    {0, 0, 0, 0, 0, 5}
            };
            case 2 -> new int[][]{
                    { 2, 2, 2, 3, 0, 4},
                    { 0, 0, 5, 3, 0, 4},
                    { 1, 1, 5, 0, 0, 7},
                    { 0, 0, 5, 6, 6, 7},
                    {10,10,10, 0, 8, 0},
                    { 0, 9, 9, 0, 8, 0}
            };
            case 3 -> new int[][]{
                    { 2, 2, 3, 4, 0, 0},
                    { 5, 0, 3, 4, 0, 0},
                    { 5, 1, 1, 4, 0, 0},
                    { 5, 0, 0, 7, 7, 7},
                    { 6, 6, 6, 0, 9, 8},
                    {10,10, 0, 0, 9, 8}
            };
            case 4 -> new int[][]{
                    { 2, 3, 4, 4, 5, 6},
                    { 2, 3, 0, 0, 5, 6},
                    { 8, 1, 1,14, 0, 7},
                    { 8, 9, 9,14, 0, 7},
                    { 8, 0,10,11,11,11},
                    {13,13,10,12,12,12}
            };
            default -> throw new IllegalStateException("Unexpected value: " + difficulte);
        };
        this.listeNumVoiture = switch (difficulte) {
            case 1 -> new int[]{1, 2, 3, 4, 5};
            case 2, 3 -> new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            case 4 -> new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
            default -> throw new IllegalStateException("Unexpected value: " + difficulte);
        };
    }

    public int[] getListeNumVoiture() {
        return listeNumVoiture;
    }

    public int[][] getCarte() {
        return carte;
    }

    public RushHourState(int[][] carte, int[] listeNumVoiture) {
        // Clone approfondi du tableau
        this.carte = new int[carte.length][];
        for (int i = 0; i < carte.length; i++) {
            this.carte[i] = carte[i].clone();
        }

        this.listeNumVoiture = listeNumVoiture;

    }

    @Override
    protected RushHourState cloneState() {
        return new RushHourState(carte, listeNumVoiture);
    }

    @Override
    protected boolean equalsState(State o) {
        return Arrays.deepEquals(carte, ((RushHourState) o).getCarte());
    }


    @Override
    public boolean equals(Object o) {
        return Arrays.deepEquals(carte, ((RushHourState) o).carte);
    }

    @Override
    protected int hashState() {
        return Arrays.deepHashCode(carte);
    }

    //Heuristique v1: calcul distance entre voiture rouge et arrivée

    // Heuristique v2
//    @Override
//    public double getHeuristic() {
//        // Distance entre la voiture 1 et la sortie + estimation du cout de libération
//        int heuristique = 0;
//        int ligne = coorDonneesSortie[0];
//        int colonne = coorDonneesSortie[1];
//        while (getValueAt(ligne, colonne) != 1) {
//            heuristique++;
//            //Estimation du cout de liberation de la case (ligne, colonne)
//            if (getValueAt(ligne, colonne) != 0) {
//                heuristique++;
//                // Vérification des cases au dessus et en dessous de la case pour estimer en combien de coûp on peut libérer la case
//                //Premier ligne
//                int valeurLigne0 = getValueAt(0,colonne);
//
//                int valeurLigne1 = getValueAt(1,colonne);
//                //Ligne de sortie
//                int valeurLigne2 = getValueAt(2,colonne);
//
//                int valeurLigne3 = getValueAt(3,colonne);
//                int valeurLigne4 = getValueAt(4,colonne);
//
//                int valeurDegagementHaut;
//                if(valeurLigne0 == 0 && valeurLigne1 == 0){
//                    valeurDegagementHaut = 2;
//                }else if (valeurLigne0 == 0 && valeurLigne1 == valeurLigne2){
//                    valeurDegagementHaut = 1;
//                } else if (valeurLigne0 != 0 && valeurLigne1 == valeurLigne2){
//                    valeurDegagementHaut = 2;
//                }else if (valeurLigne0 == 0 || valeurLigne1 == 0){
//                    valeurDegagementHaut = 3;
//                }else valeurDegagementHaut = 4;
//
//                int valeurDegagementBas;
//                if(valeurLigne3 == 0 && valeurLigne4 == 0){
//                    valeurDegagementBas = 2;
//                } else if (valeurLigne4== 0 && valeurLigne2== valeurLigne3) {
//                    valeurDegagementBas = 1;
//                } else if (valeurLigne4 != 0 && valeurLigne2== valeurLigne3) {
//                    valeurDegagementBas = 2;
//                } else if (valeurLigne4 == 0 || valeurLigne3 == 0) {
//                    valeurDegagementBas = 3;
//                } else valeurDegagementBas = 4;
//                //On ajoute à l'heuristique la plus petite valeur entre valeurDegagementBas et valeurDegagementHaut
//                heuristique += Math.min(valeurDegagementBas,valeurDegagementHaut);
//            }
//
//            colonne--;
//        }
//        return heuristique;
//    }

    // Heuristique v3
    @Override
    public double getHeuristic() {
        int heuristique = 0;

        // Distance entre la voiture 1 et la sortie
        int ligne = coorDonneesSortie[0];
        int colonne = coorDonneesSortie[1];
        while (getValueAt(ligne, colonne) != 1) {
            heuristique += 1;

            // Estimation du coût de libération
            if (getValueAt(ligne, colonne) != 0) {
                heuristique += coutLiberation(ligne, colonne);
            }

            colonne--;
        }

        return heuristique;
    }

    private int coutLiberation(int ligne, int colonne) {
        int valeurDegagementHaut = coutLiberationDirection(ligne, colonne, -1);
        int valeurDegagementBas = coutLiberationDirection(ligne, colonne, 1);

        return Math.min(valeurDegagementHaut, valeurDegagementBas);
    }

    private int coutLiberationDirection(int ligne, int colonne, int direction) {
        int valeurVoisin = getValueAt(ligne + direction, colonne);

        if (valeurVoisin == 0) {
            return 3;
        } else if (valeurVoisin == getValueAt(ligne + 2 * direction, colonne)) {
            return 2;
        } else {
            return 5;
        }
    }

    public String toString() {
        String res = "";
        for (int[] ints : carte) {
            for (int j = 0; j < carte[0].length; j++)
                res += "| " + ints[j] + " ";
            res += "|\n";
            res += Misc.dupString("+---", carte[0].length);
            res += "+\n";
        }
        return res;
    }

    /**
     * @param coordonnees coordonnees[0] = ligne, coordonnees[1] = colonne
     * @param a
     * @return
     */
    public boolean isLegal(int[] coordonnees, Action a) {
        int valeur = carte[coordonnees[0]][coordonnees[1]];
        if (a == LEFT) {
            // On vérifie qu'il n'est pas tout à gauche
            if (coordonnees[1] == 0) return false;
            int valeurVoisin = carte[coordonnees[0]][coordonnees[1] - 1];
            if (valeurVoisin == valeur) {
                return isLegal(new int[]{coordonnees[0], coordonnees[1] - 1}, a);
            } else {
                // Vérification de l'orientation de la voiture
                // On retourne faux si la case est tout a droite ou si la case de droite n'est pas identitque au véhicule
                if (coordonnees[1] == 5 || carte[coordonnees[0]][coordonnees[1] + 1] != valeur) return false;
                return valeurVoisin == 0;
            }
        }
        if (a == RIGHT) {
            if (coordonnees[1] == 5) return false;
            int valeurVoisin = carte[coordonnees[0]][coordonnees[1] + 1];
            if (valeurVoisin == valeur) {
                return isLegal(new int[]{coordonnees[0], coordonnees[1] + 1}, a);
            } else {
                // Vérification de l'orientation de la voiture
                if (coordonnees[1] == 0 || carte[coordonnees[0]][coordonnees[1] - 1] != valeur) return false;
                return valeurVoisin == 0;
            }
        }
        if (a == UP) {
            if (coordonnees[0] == 0) return false;
            int valeurVoisin = carte[coordonnees[0] - 1][coordonnees[1]];
            if (valeurVoisin == valeur) {
                return isLegal(new int[]{coordonnees[0] - 1, coordonnees[1]}, a);
            } else {
                if (coordonnees[0] == 5 || carte[coordonnees[0] + 1][coordonnees[1]] != valeur) return false;
                return valeurVoisin == 0;
            }
        }
        if (a == DOWN) {
            if (coordonnees[0] == 5) return false;
            int valeurVoisin = carte[coordonnees[0] + 1][coordonnees[1]];
            if (valeurVoisin == valeur) {
                return isLegal(new int[]{coordonnees[0] + 1, coordonnees[1]}, a);
            } else {
                if (coordonnees[0] == 0 || carte[coordonnees[0] - 1][coordonnees[1]] != valeur) return false;
                return valeurVoisin == 0;
            }
        }
        return false;
    }

    public int[] getCoordonneesVoiture(int numVoiture) {
        for (int i = 0; i < carte.length; i++) {
            for (int j = 0; j < carte[0].length; j++) {
                if (carte[i][j] == numVoiture) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    /**
     * Déplacer le véhicule vers la droite
     */
    public void moveRight(int[] vehicule) {
        int ligne = vehicule[0];
        int colonne = vehicule[1];
        int valeur = carte[ligne][colonne];
        colonne++;
        int valeurVoisin = carte[ligne][colonne];
        while (valeurVoisin == valeur) {
            colonne++;
            valeurVoisin = carte[ligne][colonne];
        }
        carte[ligne][colonne] = valeur;

        // Suppression de la queue de la voiture
        ligne = vehicule[0];
        colonne = vehicule[1];
        while (colonne > 0 && carte[ligne][colonne - 1] == valeur) {
            colonne--;
        }
        carte[ligne][colonne] = 0;
    }

    /**
     * Déplacer le véhicule vers la gauche
     */
    public void moveLeft(int[] vehicule) {
        int ligne = vehicule[0];
        int colonne = vehicule[1];
        int valeur = carte[ligne][colonne];
        colonne--;
        int valeurVoisin = carte[ligne][colonne];
        while (valeurVoisin == valeur) {
            colonne--;
            valeurVoisin = carte[ligne][colonne];
        }
        carte[ligne][colonne] = valeur;

        ligne = vehicule[0];
        colonne = vehicule[1];
        while (colonne < 5 && carte[ligne][colonne + 1] == valeur) {
            colonne++;
        }
        carte[ligne][colonne] = 0;
    }

    /**
     * Déplacer le véhicule vers le haut
     */
    public void moveUp(int[] vehicule) {
        int ligne = vehicule[0];
        int colonne = vehicule[1];
        int valeur = carte[ligne][colonne];
        ligne--;
        int valeurVoisin = carte[ligne][colonne];
        while (valeurVoisin == valeur) {
            ligne--;
            valeurVoisin = carte[ligne][colonne];
        }
        carte[ligne][colonne] = valeur;

        ligne = vehicule[0];
        colonne = vehicule[1];
        while (ligne < 5 && carte[ligne + 1][colonne] == valeur) {
            ligne++;
        }
        carte[ligne][colonne] = 0;
    }

    /**
     * Déplacer le véhicule vers le bas
     */
    public void moveDown(int[] vehicule) {
        int ligne = vehicule[0];
        int colonne = vehicule[1];
        int valeur = carte[ligne][colonne];
        ligne++;
        int valeurVoisin = carte[ligne][colonne];
        while (valeurVoisin == valeur) {
            ligne++;
            valeurVoisin = carte[ligne][colonne];
        }
        carte[ligne][colonne] = valeur;

        ligne = vehicule[0];
        colonne = vehicule[1];
        while (ligne > 0 && carte[ligne - 1][colonne] == valeur) {
            ligne--;
        }
        carte[ligne][colonne] = 0;
    }


    public int getValueAt(int ligne, int colonne) {
        return carte[ligne][colonne];
    }

}
