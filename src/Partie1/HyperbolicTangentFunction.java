package Partie1;

public class HyperbolicTangentFunction implements TransferFunction {

    /**
     * Fonction tangente hyperbolique
     *
     * @param value Entrée
     * @return Sortie de la fonction tangente hyperbolique sur l'entrée
     */
    @Override
    public double evaluate(double value) {
        return Math.tanh(value);
    }

    /**
     * Dérivée de la fonction tangente hyperbolique
     *
     * @param value Entrée
     * @return Sortie de la dérivée de la fonction tangente hyperbolique sur l'entrée
     */
    @Override
    public double evaluateDer(double value) {
        double tanh = evaluate(value);
        return 1.0 - tanh * tanh;
    }
}

