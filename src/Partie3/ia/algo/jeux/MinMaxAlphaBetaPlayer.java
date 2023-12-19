package Partie3.ia.algo.jeux;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.ActionValuePair;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.jeux.Game;
import Partie3.ia.framework.jeux.GameState;
import Partie3.ia.framework.jeux.Player;

public class MinMaxAlphaBetaPlayer extends Player {

    int nbEtat;

    private static final int profondeurMax = 100;

    private int profondeur;

    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMaxAlphaBetaPlayer(Game g, boolean player_one) {
        super(g, player_one);
        nbEtat = 0;
        name = "alphabeta";
    }


    @Override
    public Action getMove(GameState state) {
        profondeur= 0;
        if (player == PLAYER1) {
            return MaxValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        } else {
            return MinValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        }
    }

    public ActionValuePair MaxValue(GameState state, double alpha, double beta) {
        if (game.endOfGame(state) || profondeur == profondeurMax) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double V_max = Double.NEGATIVE_INFINITY;
        Action C_max = null;
        profondeur++;
        for (Action c : game.getActions(state)) {
            nbEtat++;
            //System.out.println("nbEtat : " + nbEtat);
            State S_suivant = game.doAction(state, c);
            ActionValuePair c_suivant = MinValue((GameState) S_suivant, alpha, beta);
            double v = c_suivant.getValue();
            if (v > V_max) {
                V_max = v;
                C_max = c;

                //si V_max > alpha
                if (V_max > alpha) {
                    alpha = V_max;
                }
            }

            if (V_max >= beta) {
                return new ActionValuePair(C_max, V_max);
            }
        }
        return new ActionValuePair(C_max, V_max);
    }

    public ActionValuePair MinValue(GameState state, double alpha, double beta) {
        if (game.endOfGame(state) || profondeur == profondeurMax) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double V_min = Double.POSITIVE_INFINITY;
        Action C_min = null;
        profondeur++;
        for (Action c : game.getActions(state)) {
            nbEtat++;
            //System.out.println("nbEtat : " + nbEtat);
            State S_suivant = game.doAction(state, c);
            ActionValuePair c_suivant = MaxValue((GameState) S_suivant, alpha, beta);
            double v = c_suivant.getValue();
            if (v < V_min) {
                V_min = v;
                C_min = c;
                if (V_min < beta) {
                    beta = V_min;
                }
            }
            if (V_min <= alpha) {
                return new ActionValuePair(C_min, V_min);
            }
        }
        return new ActionValuePair(C_min,V_min);
    }


}
