package personalSolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Main_Perso {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        /*
        Lire l'image, initialisation.
         */
        String fichier = "originale.jpg";
        int nb_couleurs = 3;
        String file_name = "./bi_limite_"+nb_couleurs+".png";

        switch (args.length){
            case 3:
                file_name = args[2];
            case 2:
                nb_couleurs = Integer.parseInt(args[1]);
            case 1:
                fichier = args[0];
        }


        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(fichier));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /*
        Créer trois Map correspondant respectivement à R, G et B, et initialiser les clés dans la plage de 0 à 255.
         */
        HashMap<Integer, Integer> red_map = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> blue_map = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> green_map = new HashMap<Integer, Integer>();

        for(int i = 0; i < 256; i ++){
            red_map.put(i,0);
            blue_map.put(i,0);
            green_map.put(i,0);
        }

        /*
        Analyser les valeurs RGB de chaque pixel de l'image et ajouter le nombre d'occurrences correspondant
        dans les Maps respectives.
        Le nombre de pixels associés à chaque valeur de R, G ou B.
         */
        for(int x = 0; x < bi.getWidth(); x++){
            for(int y = 0; y < bi.getHeight(); y++){
                int[] rgbs = Outil.analyseRGB(bi.getRGB(x,y));
                red_map.put(rgbs[0], red_map.get(rgbs[0]) + 1);
                green_map.put(rgbs[1], green_map.get(rgbs[1]) + 1);
                blue_map.put(rgbs[2], blue_map.get(rgbs[2]) + 1);
            }
        }

        /*
        En fonction du nombre de couleurs, obtenir de manière aléatoire les valeurs de x n fois
        et les stocker dans une liste pour une utilisation lors de l'évalution.
         */
        ArrayList<Integer> x_reds = new ArrayList<>();
        ArrayList<Integer> x_greens = new ArrayList<>();
        ArrayList<Integer> x_blues = new ArrayList<>();
        for(int i = 0; i < nb_couleurs; i++){
            x_reds.add(Outil.randomX(red_map, bi.getWidth(),bi.getHeight()));
            x_greens.add(Outil.randomX(green_map,bi.getWidth(),bi.getHeight()));
            x_blues.add(Outil.randomX(blue_map,bi.getWidth(),bi.getHeight()));
        }


        //Pas
        int dx = 2;
        //Efficacité d'apprentissage
        int alpha = 2;
        //Nb d'iterations
        int epoch = 100;

        /*
        Evalution
         */
        for(int fois = 0 ; fois < epoch ; fois++){
            System.out.println("--Epoch"+fois+"--");

            for(int index_x = 0; index_x < nb_couleurs ; index_x++){
                //Red
                evalution(red_map, x_reds, dx, index_x, alpha);

                //Green
                evalution(green_map, x_greens, dx, index_x, alpha);

                //Blue
                evalution(blue_map, x_blues, dx, index_x, alpha);
            }

        }

        System.out.println("Waiting...");


        ArrayList<CD> cds = new ArrayList<CD>();

        /*
        Combinaisons, Calculer la distance pour chaque combinaison.
         */
        for(int r : x_reds){
            for(int g : x_greens){
                for(int b : x_blues){
                    Color c = new Color(r,g,b);
                    long distances = 0;
                    for(int x = 0; x < bi.getWidth(); x++){
                        for(int y = 0; y < bi.getHeight(); y++){
                            distances += Outil.distanceDeuxCouleurs(c.getRGB(),bi.getRGB(x,y));
                        }
                    }
                    cds.add(new CD(c,distances));
                }
            }
        }

        /*
        Tri, placer la combinaison avec la distance minimale au début du tableau.
         */
        cds.sort(new Comparator<CD>() {
            @Override
            public int compare(CD o1, CD o2) {
                return Long.compare(o1.getDistances(), o2.getDistances());
            }
        });

        /*
        Selon le nombre de couleurs, choisir les n premières couleurs
         */
        ArrayList<Color> colors = new ArrayList<Color>();
        for (int i = 0; i < nb_couleurs; i++){
            colors.add(cds.get(i).getColor());
        }

        /*
        Pour chaque pixel de l'image, choisir la couleur de la liste qui est la plus proche en termes de distance,
        et la remplir dans l'image à écrire.
        */
        BufferedImage bi_limite = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_3BYTE_BGR);

        for(int x = 0; x < bi.getWidth();x++){
            for(int y = 0; y < bi.getHeight();y++){
                int[] rgb_bi = Outil.analyseRGB(bi.getRGB(x,y));
                Color c_bi = new Color(rgb_bi[0],rgb_bi[1],rgb_bi[2]);
                Color resultat = null;
                long min_distance = Integer.MAX_VALUE;
                for(Color c : colors){
                    long d = Outil.distanceDeuxCouleurs(c.getRGB(),c_bi.getRGB());
                    if(min_distance > d){
                        min_distance = d;
                        resultat = c;
                    }
                }
                assert resultat != null;
                bi_limite.setRGB(x,y,resultat.getRGB());
            }
        }

        /*
        Enregistrer l'image
         */
        try {
            ImageIO.write(bi_limite, "PNG", new File(file_name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        System.out.println("Temps calculé: "+(end-start)+"ms");
    }

    /**
     * Selon la Map, mettre à jour les valeurs à l'intérieur de list_x en utilisant la méthode de descente de gradient,
     * afin de minimiser autant que possible la distance finale.
     * (la distance fait référence à la distance entre les valeurs des éléments de list_x et les valeurs dans l'histogramme)
     * @param map l’histogramme de la valeur de R, G ou B
     * @param x_list le tableau contenant les valeurs de la solution locale optimale
     * @param dx le pas
     * @param index_x l'indice de list_x, utilisé pour parcourir tous les éléments de list_x
     * @param alpha l'efficacité d'apprentissage
     */
    private static void evalution(HashMap<Integer, Integer> map, ArrayList<Integer> x_list, int dx, int index_x, int alpha) {
        int x = x_list.get(index_x);

        int x_moins = x - dx;
        int x_plus = x + dx;

        ArrayList<Integer> dm = new ArrayList<>(x_list);
        dm.remove(Integer.valueOf(x));
        dm.add(x_moins);

        ArrayList<Integer> dp = new ArrayList<>(x_list);
        dp.remove(Integer.valueOf(x));
        dp.add(x_plus);

        long distance_moins = 0;
        long distance_plus = 0;

        for(int i = 0; i < 256 ; i++){
            for(int j = 0; j < map.get(i);j++){
                distance_plus += Outil.distance_proche(i,dp);
                distance_moins += Outil.distance_proche(i,dm);
            }
        }

        if(distance_plus > distance_moins){
            x_list.set(index_x, Math.max((x - (dx * alpha)), 0));
        }else {
            x_list.set(index_x, Math.min((x + (dx * alpha)), 255));
        }
    }
}
