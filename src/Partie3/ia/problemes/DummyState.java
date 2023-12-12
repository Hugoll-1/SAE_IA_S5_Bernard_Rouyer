package Partie3.ia.problemes;

import Partie3.ia.framework.common.State;

/**
 * Représente un état du problème générique
 *
 */

public class DummyState extends State {

    // Un nœud est représenté par un entier
    private int id;

    /**
     * Créer un état du problème générique
     * <p>A utiliser pour créer l'état initial (id=0)</p>
     */
    
    public DummyState (){
        id = 0; 
    }

    /**
     * Créer un état du problème générique
     * <p>A utiliser pour créer un état quelconque</p>
     *@param s l'identifiant de l'état (le nœud du graph)
     */
    
    public DummyState (int s){
        id = s;
    }
    
	public boolean equalsState(State o) {
        return id == ((DummyState) o).id;
    }
    
    public DummyState cloneState() {
        return new DummyState(id);
	}
    
    public int hashState() {
        return Integer.hashCode(id);
    }
    
    @Override
	public String toString() {
        return ""+id;
    }
}
