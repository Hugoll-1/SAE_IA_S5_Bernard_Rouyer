package Partie3.ia.algo.recherche;

import Partie3.ia.framework.common.Action;
import Partie3.ia.framework.common.ArgParse;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.recherche.SearchNode;
import Partie3.ia.framework.recherche.SearchProblem;
import Partie3.ia.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class UCS extends TreeSearch {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public UCS(SearchProblem p, State s) {
        super(p, s);
        // Initialisation de la frontière avec une PriorityQueue basée sur le coût
        frontier = new PriorityQueue<>(new SearchNodeComparator());
    }

    @Override
    public boolean solve() {
        System.out.println("Algo choisi: UCS");
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        State state = node.getState();

        // On commence à l'état initial
        frontier.add(node);

        // On initialise l'ensemble des nœuds déjà explorés a vide
        explored.clear();

        if (ArgParse.DEBUG)
            System.out.print("[" + state);

        while (!frontier.isEmpty()){
            // Stratégie: UCS
            node = frontier.poll();
            if(ArgParse.DEBUG){
                System.out.println(node);
            }

            // Si le nœud contient un état but
            if (problem.isGoalState(node.getState())) {
                // On enregistre le nœud final
                end_node = node;
                // On retourne vrai
                return true;
            } else {
                // On ajoute l'état du nœud dans l'ensemble des nœuds explorés
                explored.add(node.getState());

                // Les actions possibles depuis cet état
                ArrayList<Action> actions = problem.getActions(node.getState());

                // Pour chaque nœud enfant
                for (Action a : actions) {
                    // Nœud enfant
                    SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);

                    // S'il n'est pas dans la frontière et si son état n'a pas été visité
                    if (!frontier.contains(child) && !explored.contains(child.getState())) {
                        // L'insérer dans la frontière avec la priorité du coût
                        frontier.add(child);
                    }else if(frontier.contains(child)){
                        // Si le nœud est déjà dans la frontière
                        // On récupère le nœud de la frontière
                        SearchNode frontier_node = frontier.stream().filter(n -> n.equals(child)).findFirst().get();
                        // Si le cout du nœud enfant est inférieur au cout du nœud de la frontière
                        if(child.getCost() < frontier_node.getCost()){
                            // On le remplace
                            frontier.remove(frontier_node);
                            frontier.add(child);
                        }
                    }
                }
            }
        }

        // Pas de solutions trouvées
        return false;


    }

    // Comparator par le cout
    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return Double.compare(node1.getCost(), node2.getCost());
        }
    }
}
