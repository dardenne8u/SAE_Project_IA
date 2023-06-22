package personalSolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        String fichier = args[0];
        int nb_couleurs = Integer.parseInt(args[1]);

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(fichier));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        HashMap<Integer, Integer> red_map = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> blue_map = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> green_map = new HashMap<Integer, Integer>();

        for(int i = 0; i < 256; i ++){
            red_map.put(i,0);
            blue_map.put(i,0);
            green_map.put(i,0);
        }

        for(int x = 0; x < bi.getWidth(); x++){
            for(int y = 0; y < bi.getHeight(); y++){
                int[] rgbs = Outil.analyseRGB(bi.getRGB(x,y));
                red_map.put(rgbs[0],red_map.get(rgbs[0]) + 1);
                green_map.put(rgbs[1],green_map.get(rgbs[1]) + 1);
                blue_map.put(rgbs[2],blue_map.get(rgbs[2]) + 1);
            }
        }


        ArrayList<Integer> x_reds = new ArrayList<>();
        ArrayList<Integer> x_greens = new ArrayList<>();
        ArrayList<Integer> x_blues = new ArrayList<>();
        for(int i = 0; i < nb_couleurs; i++){
            x_reds.add(Outil.randomX(red_map));
            x_greens.add(Outil.randomX(green_map));
            x_blues.add(Outil.randomX(green_map));
        }


        int pas = 2;
        int alpha = 2;
        int dx = pas * alpha;
        int epoch = 100;


        for(int fois = 0 ; fois < epoch ; fois++){
            System.out.println("----Epoch"+fois+"--");

            for(int index_x = 0; index_x < nb_couleurs ; index_x++){
                //Red
                evalution(red_map, x_reds, dx, index_x);

                //Green
                evalution(green_map, x_greens, dx, index_x);

                //Blue
                evalution(blue_map, x_blues, dx, index_x);
            }

        }

        System.out.println("X Red:");
        for(int i = 0 ; i < nb_couleurs ; i++){
            System.out.println(x_reds.get(i));
        }

        System.out.println("X Green:");
        for(int i = 0 ; i < nb_couleurs ; i++){
            System.out.println(x_greens.get(i));
        }

        System.out.println("X Blue:");
        for(int i = 0 ; i < nb_couleurs ; i++){
            System.out.println(x_blues.get(i));
        }


        ArrayList<CD> cds = new ArrayList<CD>();

        for(int r : x_reds){
            for(int g : x_greens){
                for(int b : x_blues){
                    Color c = new Color(r,g,b);
                    long distances = 0;
                    for(int x = 0; x < bi.getWidth(); x++){
                        for(int y = 0; y < bi.getHeight(); y++){
                            distances += Outil.distanceDeuxCouleur(c.getRGB(),bi.getRGB(x,y));
                        }
                    }
                    cds.add(new CD(c,distances));
                }
            }
        }

        cds.sort(new Comparator<CD>() {
            @Override
            public int compare(CD o1, CD o2) {
                return Long.compare(o1.getDistances(), o2.getDistances());
            }
        });

        ArrayList<Color> colors = new ArrayList<Color>();

        for (int i = 0; i < nb_couleurs; i++){
            colors.add(cds.get(i).getColor());
        }


        BufferedImage bi_limite = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_3BYTE_BGR);

        for(int x = 0; x < bi.getWidth();x++){
            for(int y = 0; y < bi.getHeight();y++){
                System.out.println("image x:"+x+" y:"+y);
                int[] rgb_bi = Outil.analyseRGB(bi.getRGB(x,y));
                Color c_bi = new Color(rgb_bi[0],rgb_bi[1],rgb_bi[2]);
                Color resultat = null;
                long min_distance = Integer.MAX_VALUE;
                for(Color c : colors){
                    long d = Outil.distanceDeuxCouleur(c.getRGB(),c_bi.getRGB());
                    if(min_distance > d){
                        min_distance = d;
                        resultat = c;
                    }
                }
                assert resultat != null;
                bi_limite.setRGB(x,y,resultat.getRGB());
            }
        }

        String file_name = "./bi_limite_"+nb_couleurs+".png";
        try {
            ImageIO.write(bi_limite, "PNG", new File(file_name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void evalution(HashMap<Integer, Integer> map, ArrayList<Integer> x, int dx, int index_x) {
        int xr = x.get(index_x);

        int xr_moins = xr - dx;
        int xr_plus = xr + dx;

        ArrayList<Integer> dmr = new ArrayList<>(x);
        dmr.remove(Integer.valueOf(xr));
        dmr.add(xr_moins);

        ArrayList<Integer> dpr = new ArrayList<>(x);
        dpr.remove(Integer.valueOf(xr));
        dpr.add(xr_plus);

        long distance_moins_r = 0;
        long distance_plus_r = 0;

        for(int i = 0; i < 256 ; i++){
            for(int j = 0; j < map.get(i);j++){
                distance_plus_r += Outil.distance_proche(i,dpr);
                distance_moins_r += Outil.distance_proche(i,dmr);
            }
        }

        if(distance_plus_r > distance_moins_r){
            x.set(index_x, xr_moins);
        }else {
            x.set(index_x, xr_plus);
        }
    }
}
