package saeSolution;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KMeans {


    /**
     * Liste des points a grouper
     */
    private List<Point> points;

    /**
     * Liste des clusters
     */
    private List<Cluster> clusters;

    /**
     * Constructeur
     * @param points liste des points a grouper
     * @param nombreClusters nombre de clusters
     */
    public KMeans(List<Point> points, int nombreClusters) {
        this.points = points;

        // Initialisation des centroides
        this.clusters = new ArrayList<>();
        for (int i = 0; i < nombreClusters; i++) {

            // valeur aléatoire du centroid
            int red = (int) Math.floor(Math.random() * 256);
            int green = (int) Math.floor(Math.random() * 256);
            int blue = (int) Math.floor(Math.random() * 256);

            Point centroid = new Point(red, green, blue, -1, -1);


            clusters.add(new Cluster(centroid));
        }
    }


    public void run() {
        // cout de l'ancienne iteration
        long old_cout = Integer.MAX_VALUE;

        // cout de l'iteration actuelle
        long cout = cout();

        /*
            * Tant que le cout de l'iteration actuelle est inferieur au cout de l'ancienne iteration
            * et que le cout de l'ancienne iteration est different de 0 (pour la premiere iteration)
         */
        while (old_cout > cout || old_cout == 0) {

            // Initialisation Groupes
            for (Cluster c : clusters) {
                c.resetPoint();
            }

            // Construction des groupes
            for (Point p : points) {
                Cluster cluster = null;
                int distance = Integer.MAX_VALUE;

                // Recherche du centroid le plus proche
                for (Cluster c : clusters) {
                    int d = p.distance(c.getCentroid());
                    if (d < distance) {
                        distance = d;
                        cluster = c;
                    }
                }
                // Ajout du point au groupe
                cluster.add(p);
            }

            // Mise a jour des centroides
            for (Cluster c : clusters) {
                c.barycentre();
            }
            old_cout = cout;
            cout = cout();
        }
    }

    /**
     * Calcul le cout total des clusters
     * @return le cout total des clusters
     */
    public long cout() {
        long cout = 0;
        for (Cluster c : clusters) {
            cout += c.cout();
        }
        return cout;
    }

    /**
     * @return la liste des clusters
     */
    public List<Cluster> getClusters() {
        return clusters;
    }

    public static void main(String[] args) {

        List<Point> points = new ArrayList<>();
        BufferedImage image = null;

        // Nombre de clusters par defaut
        int nombreClusters = 10;

        // Si un argument est passe en parametre, on le prend en compte
        if (args.length > 0)
            nombreClusters = Integer.parseInt(args[0]);
        // Si l'argument est negatif, on quitte
        if (nombreClusters < 0) {
            System.out.println("Le nombre de clusters doit être positif");
            System.exit(1);
        }

        // Lecture de l'image
        try {
            image = ImageIO.read(new File("resources/animaux/perroquet_small.png"));
        } catch (IOException e) {
            System.out.println("Impossible de lire l'image");
            System.exit(1);
        }

        // Creation de la liste des points
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                Point p = new Point(rgb, i, j);
                points.add(p);
            }
        }

        // Lancement de l'algorithme
        long beginTime = System.currentTimeMillis();
        KMeans kmeans = new KMeans(points, nombreClusters);
        kmeans.run();
        long endTime = System.currentTimeMillis();

        // Affichage du temps d'execution de l'algorithme
        System.out.println("Temps d'exécution : " + (endTime - beginTime) + " ms");

        // Coloration de l'image
        for (Cluster cluster : kmeans.getClusters()) {
            for (Point p : cluster.getGroupe()) {
                int[] pos = p.getPosition();
                Point centroid = cluster.getCentroid();
                Color color = new Color(
                        centroid.getRed(),
                        centroid.getGreen(),
                        centroid.getBlue()
                );
                image.setRGB(pos[0], pos[1], color.getRGB());
            }
        }

        // Ecriture de l'image
        try {
            ImageIO.write(image, "png", new File("resources/out/perroquet_small_"+ nombreClusters+".png"));
        } catch (IOException e) {
            System.out.println("Impossible d'écrire l'image");
            System.exit(1);
        }


    }
}
