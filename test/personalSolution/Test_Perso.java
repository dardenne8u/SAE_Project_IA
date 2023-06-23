package personalSolution;

import java.util.ArrayList;

public class Test_Perso {

    public static void main(String[] args) {
        ArrayList<String> list_fichier_in = new ArrayList<>();
        list_fichier_in.add("mario_small.png");
        list_fichier_in.add("ours.png");
        list_fichier_in.add("Stranger_Things_logo.png");
        list_fichier_in.add("originale.jpg");
        list_fichier_in.add("van-Gogh-Night_small.png");

        int nbColors = 10;
        for(String fichier_in : list_fichier_in){
            String[] pargs = new String[3];
            pargs[0] = fichier_in;
            pargs[1] = String.valueOf(nbColors);
            pargs[2] = nbColors + "_" + fichier_in;

            long start = System.currentTimeMillis();

            int fois = 3;
            for(int i = 0; i < fois; i++){
                Main_Perso.main(pargs);
            }
            long end = System.currentTimeMillis();
            System.out.println(fichier_in+" Temp calculé avg:"+((end - start)/fois)+"ms");
        }
    }
}
