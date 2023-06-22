package personalSolution;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Outil {

    public static int[] analyseRGB(int color){
        return new int[]{(color & 0xFF0000) >> 16, (color & 0xFF00) >> 8, color & 0xFF};
    }

    public static long distanceDeuxCouleur(int color1, int color2){
        int[] rgbs1 = analyseRGB(color1);
        int[] rgbs2 = analyseRGB(color2);
        return ((long) (rgbs1[0] - rgbs2[0]) * (rgbs1[0] - rgbs2[0]) + (long) (rgbs1[1] - rgbs2[1]) * (rgbs1[1] - rgbs2[1]) + (long) (rgbs1[2] - rgbs2[2]) * (rgbs1[2] - rgbs2[2]));
    }

    public static int randomX(HashMap<Integer, Integer> map){
        int valueRandom = new Random().nextInt(640000);
        for(int i = 0; i < 256 ; i++){
            valueRandom -= map.get(i);
            if(valueRandom <= 0){
                return i;
            }
        }
        throw new Error("Unknown error occurred in randomX");
    }

    public static int distance_proche(int target, ArrayList<Integer> param){

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
