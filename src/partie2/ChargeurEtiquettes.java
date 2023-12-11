package partie2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChargeurEtiquettes {
    public static List<Integer> chargerEtiquettes(String nomFichier, int maxVal) throws IOException {
        List<Integer> labels = new ArrayList<>();

        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("./img/" + nomFichier));

        // Lire le num magique
        int magicNumber = dataInputStream.readInt();
        if (magicNumber != 2049) {
            throw new Error("Type de fichier incorrect");
        }

        // Lire le nombre d'éléments dans le fichier
        int numLabels = dataInputStream.readInt();
        if(maxVal != -1 && maxVal< numLabels){
            numLabels = maxVal;
        }

        // Pour chaque octet, ajouter l'element dans la liste
        for (int i = 0; i < numLabels; i++) {
            int label = dataInputStream.readUnsignedByte();
            labels.add(label);
        }

        dataInputStream.close();

        return labels;
    }

//    public static void main(String[] args) throws IOException {
//        String nomFichier = "train-labels.idx1-ubyte";
//        List<Integer> labels = chargerEtiquettes(nomFichier);
//        System.out.println(labels.get(0));
//        System.out.println(labels.get(labels.size()-1));
//
//    }
}
