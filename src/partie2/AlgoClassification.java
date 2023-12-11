package partie2;

public abstract class AlgoClassification {
    protected Donnees donnees;

    public AlgoClassification(Donnees donnees) {
        this.donnees = donnees;
    }

    public abstract int predireEtiquette(Imagette imagette);

}
