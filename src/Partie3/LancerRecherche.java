package Partie3;

import Partie3.ia.framework.common.ArgParse;
import Partie3.ia.framework.common.State;
import Partie3.ia.framework.recherche.SearchProblem;
import Partie3.ia.framework.recherche.TreeSearch;
import Partie3.ia.problemes.RushHourState;

import java.util.Arrays;

/**
 * Lance un algorithme de recherche  
 * sur un problème donné et affiche le résultat
 */
public class LancerRecherche {

    public static void main(String[] args){

        // fixer le message d'aide
        ArgParse.setUsage
            ("Utilisation :\n\n"
             + "java LancerRecherche [-prob problem] [-algo algoname]"
             + "[-v] [-h]\n"
             + "-prob : Le nom du problem {dum, map, vac, puz, rush}. Par défaut vac\n"
             + "-algo : L'algorithme {rnd, bfs, dfs, ucs, gfs, astar}. Par défault rnd\n"
             + "-v    : Rendre bavard (mettre à la fin)\n"
             + "-h    : afficher ceci (mettre à la fin)"
             );

        
        // récupérer les options de la ligne de commande
        String prob_name = ArgParse.getProblemFromCmd(args);
        String algo_name = ArgParse.getAlgoFromCmd(args);

        // créer un problème, un état initial et un algo
        SearchProblem p = ArgParse.makeProblem(prob_name);
        State s = ArgParse.makeInitialState(prob_name);
        TreeSearch algo = ArgParse.makeAlgo(algo_name, p, s);
        
        // résoudre
        if( algo.solve() )
            algo.printSolution();
    }
}
