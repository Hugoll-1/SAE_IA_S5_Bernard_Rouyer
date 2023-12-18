package Partie3.ia.problemes;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.recherche.SearchProblem;

import java.util.ArrayList;

public class RushHour extends SearchProblem {
    public static final Action UP = new Action("Up");
    public static final Action LEFT = new Action("Left");
    public static final Action DOWN = new Action("Down");
    public static final Action RIGHT = new Action("Right");

    public RushHour() {
        // La liste des actions possibles
        ACTIONS = new Action[]{UP, LEFT, DOWN, RIGHT};
    }

    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<Action>();
        int[] listeVoiture = ((RushHourState) s).getListeNumVoiture();
        System.out.print("Actions possible pour l'état: \n"+s);
        for (int i : listeVoiture) {
            for (Action a : ACTIONS) {
                int[] coordonneesVoiture = ((RushHourState) s).getCoordonneesVoiture(i);
                if (((RushHourState) s).isLegal(coordonneesVoiture, a)) {
                    System.out.println(i + "-" + a.getName());
                    actions.add(new Action(i + "-" + a.getName()));
                }
            }
        }
        System.out.println();
        return actions;
    }

    @Override
    public State doAction(State s, Action a) {
        // On copie l'état courant et on le modifie
        RushHourState r = (RushHourState) s.clone();

        int[] coordonneesVoiture = r.getCoordonneesVoiture(Integer.parseInt(a.getName().split("-")[0]));
        switch (a.getName().split("-")[1]) {
            case "Up" -> r.moveUp(coordonneesVoiture);
            case "Left" -> r.moveLeft(coordonneesVoiture);
            case "Down" -> r.moveDown(coordonneesVoiture);
            case "Right" -> r.moveRight(coordonneesVoiture);
            default -> throw new IllegalArgumentException("Invalid" + a);
        }
        return r;
    }

    @Override
    public boolean isGoalState(State s) {
        return ((RushHourState) s).getValueAt(2, 5) == 1;
    }

    @Override
    public double getActionCost(State s, Action a) {
        return 1.0;
    }
}
