package Partie3.ia.algo.jeux;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.ActionValuePair;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.jeux.Game;
import Partie3.ia.framework.jeux.GameState;
import Partie3.ia.framework.jeux.Player;

public class MinMaxPlayer extends Player {


    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMaxPlayer(Game g, boolean player_one) {
        super(g, player_one);
        name = "MinMax";

    }


    @Override
    public Action getMove(GameState state) {
        System.out.println("MinMax");
        System.out.println(player);
        if (player == PLAYER1) {
            return MaxValue(state).getAction();
        } else {
            return MinValue(state).getAction();
        }
    }

    public ActionValuePair MaxValue(GameState state) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        double V_max = Double.MIN_VALUE;
        Action C_max = null;
        for (Action c : game.getActions(state)) {
            State S_suivant = game.doAction(state, c);
            ActionValuePair c_suivant = MinValue((GameState) S_suivant);
            double v = c_suivant.getValue();
            if (v > V_max) {
                V_max = v;
                C_max = c;
            }
        }
        return new ActionValuePair(C_max,V_max);
    }

    public ActionValuePair MinValue(GameState state) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        Double V_min = Double.MAX_VALUE;
        Action C_min = null;
        for (Action c : game.getActions(state)) {
            State S_suivant = game.doAction(state, c);
            ActionValuePair c_suivant = MaxValue((GameState) S_suivant);
            double v = c_suivant.getValue();
            if (v < V_min) {
                V_min = v;
                C_min = c;
            }
        }
        return new ActionValuePair(C_min,V_min);
    }


}
