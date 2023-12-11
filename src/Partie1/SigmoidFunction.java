package Partie1;

public class SigmoidFunction implements TransferFunction {

    /**
     * Fonction sigmoïde
     *
     * @param value Entrée
     * @return Sortie de la fonction sigmoïde sur l'entrée
     */
    @Override
    public double evaluate(double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }

    /**
     * Dérivée de la fonction sigmoïde
     *
     * @param value Entrée
     * @return Sortie de la dérivée de la fonction sigmoïde sur l'entrée
     */
    @Override
    public double evaluateDer(double value) {
        double sigmoid = evaluate(value);
        return sigmoid - (sigmoid * sigmoid);
    }
}

