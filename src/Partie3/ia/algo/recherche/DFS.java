package Partie3.ia.algo.recherche;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.ArgParse;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.recherche.SearchNode;
import Partie3.ia.framework.recherche.SearchProblem;
import Partie3.ia.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.util.Collections.asLifoQueue;

// Profondeur
public class DFS extends TreeSearch {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public DFS(SearchProblem p, State s) {
        super(p, s);
        //Initialisation de la frontier LIFO (asLifoQueue) DeQueue
        frontier = asLifoQueue(new LinkedList<SearchNode>());
    }

    @Override
    public boolean solve() {
        System.out.println("Algo choisi: DFS");
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        State state = node.getState();

        // On commence à l'état initial
        frontier.add(node);

        // On initialise l'ensemble des nœuds déjà explorés a vide
        explored.clear();


        if (ArgParse.DEBUG)
            System.out.print("[" + state);

        while (!frontier.isEmpty()) {
            // Retirer un nœud de la frontière selon une stratégie, stratégie: hauteur
            node = frontier.poll();

            // Si le nœud contient un état but
            if (problem.isGoalState(node.getState())) {
                // On enregistre le nœud final
                end_node = node;
                // On retourne vrai
                return true;
            }else {
                // On ajoute l'état du nœud dans l'ensemble des nœuds explorés
                explored.add(node.getState());

                // Les actions possibles depuis cette état
                ArrayList<Action> actions = problem.getActions(node.getState());

                // Pour chaque nœud enfant
                for (Action a : actions) {
                    // Nœud enfant
                    SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
                    // S'il n'est pas dans la frontière et si son état n'a pas été visité
                    if (!frontier.contains(child) && !explored.contains(child.getState())) {
                        // L'insérer dans la frontière
                        frontier.add(child);
                    }
                }
            }
        }



        return false;
    }
}
