package Partie3.ia.algo.jeux;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.ActionValuePair;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.jeux.Game;
import Partie3.ia.framework.jeux.GameState;
import Partie3.ia.framework.jeux.Player;

public class MinMaxAlphaBetaPlayer extends Player {

    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMaxAlphaBetaPlayer(Game g, boolean player_one) {
        super(g, player_one);
    }


    @Override
    public Action getMove(GameState state) {
        System.out.println("MinMax");
        System.out.println(player);
        System.out.println(state);
        if (player == PLAYER1) {
            return MaxValue(state, Double.MIN_VALUE, Double.MAX_VALUE).getAction();
        } else {
            return MinValue(state, Double.MIN_VALUE, Double.MAX_VALUE).getAction();
        }
    }

    public ActionValuePair MaxValue(GameState state, double alpha, double beta) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double V_max = Double.MIN_VALUE;
        Action C_max = null;
        for (Action c : game.getActions(state)) {
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
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        Double V_min = Double.MAX_VALUE;
        Action C_min = null;
        for (Action c : game.getActions(state)) {
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
