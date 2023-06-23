package personalSolution;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Outil {

    /**
     * Analyser la valeur de getRGB() et la convertir en un tableau.
     * @param color la valeur de getRGB()
     * @return Un tableau contenant trois éléments (R : int[0] - G : int[1] - B : int[2])
     */
    public static int[] analyseRGB(int color){
        return new int[]{(color & 0xFF0000) >> 16, (color & 0xFF00) >> 8, color & 0xFF};
    }

    /**
     * Retourner la valeur de distance entre deux couleurs.
     * @param color1 la couleur à comparer 1
     * @param color2 la couleur à comparer 2
     * @return Distance (long)
     */
    public static long distanceDeuxCouleurs(int color1, int color2){
        int[] rgbs1 = analyseRGB(color1);
        int[] rgbs2 = analyseRGB(color2);
        return ((long) (rgbs1[0] - rgbs2[0]) * (rgbs1[0] - rgbs2[0]) + (long) (rgbs1[1] - rgbs2[1]) * (rgbs1[1] - rgbs2[1]) + (long) (rgbs1[2] - rgbs2[2]) * (rgbs1[2] - rgbs2[2]));
    }

    /**
     * En fonction de la Map fournie,
     * retourner aléatoirement une valeur conforme à sa distribution de probabilité (entre 0 et 255).
     * @param map Map，la clé est comprise entre 0 et 255, représentant la couleur, et la valeur de Map représente le nombre d'occurrences de cette couleur dans l'image
     * @param largeur la largeur de l'image
     * @param hauteur la hauteur de l'image
     * @return Un nombre aléatoire x, entre 0 et 255
     */
    public static int randomX(HashMap<Integer, Integer> map, int largeur, int hauteur){
        int valueRandom = new Random().nextInt(largeur * hauteur);
        for(int i = 0; i < 256 ; i++){
            valueRandom -= map.get(i);
            if(valueRandom <= 0){
                return i;
            }
        }
        throw new Error("Unknown error occurred in randomX");
    }

    /**
     * Retourner la plus petite distance entre le chiffre cible et les chiffres dans un tableau
     * @param target le chiffre cible
     * @param param le tableau
     * @return la plus petite distance (long)
     */
    public static long distance_proche(int target, ArrayList<Integer> param){

        int min = Integer.MAX_VALUE;
        for(int i : param){
            int distance = (target - i) * (target - i);
            if(distance < min){
                min = distance;
            }
        }
        return min;
    }

}
