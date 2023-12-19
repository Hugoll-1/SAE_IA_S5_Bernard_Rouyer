package Partie3.ia.algo.jeux;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.ActionValuePair;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.jeux.Game;
import Partie3.ia.framework.jeux.GameState;
import Partie3.ia.framework.jeux.Player;
import Partie3.ia.problemes.ConnectFourState;

public class MinMaxAlphaBetaPlayer extends Player {

    int nbEtat;

    private static final int profondeurMax = 25;

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
        profondeur = 0;
        if (player == PLAYER1) {
            return MaxValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        } else {
            return MinValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        }
    }

    public ActionValuePair MaxValue(GameState state, double alpha, double beta) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        if (profondeur == profondeurMax) {
            return new ActionValuePair(null, evaluate(state));
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
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        if (profondeur == profondeurMax) {
            return new ActionValuePair(null, evaluate(state));
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
        return new ActionValuePair(C_min, V_min);
    }

    private double evaluate (GameState state) {
        int[][] board = ((ConnectFourState) state).getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (int[] direction : new int[][]{{1, 0}, {0, 1}, {1, 1}, {1, -1}}) {
                    int x = i;
                    int y = j;
                    int count = 0;
                    while (estDansCadre(x, y, board.length, board[i].length) && board[x][y] == state.getPlayerToMove()) {
                        count++;
                        x += direction[0];
                        y += direction[1];
                    }
                    if (count >= 4) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    private boolean estDansCadre(int x, int y, int n, int p) {
        return x >= 0 && x < n && y >= 0 && y < p;
    }
}
