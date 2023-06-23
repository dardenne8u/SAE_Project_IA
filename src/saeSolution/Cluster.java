package saeSolution;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    /**
     * Liste des points du cluster
     */
    private List<Point> groupe;

    /**
     * Centre du cluster
     */
    private Point centroid;

    /**
     * Constructeur
     * @param centroid le centre du cluster
     */
    public Cluster(Point centroid) {
        this.centroid = centroid;
        this.groupe = new ArrayList<>();
    }

    /**
     * Calcul le cout du cluster
     * @return le cout du cluster
     */
    public long cout() {
        long cout = 0;
        for (Point p : groupe) {
            cout += p.distance(centroid);
        }
        return cout;
    }


    /**
     * Retourne le centre du cluster
     * @return le centre du cluster
     */
    public Point getCentroid() {
        return centroid;
    }

    /**
     * Ajoute un point au cluster
     * @param p le point a ajouter
     */
    public void add(Point p) {
        groupe.add(p);
    }

    /**
     * Calcul le barycentre du cluster
     */
    public void barycentre() {
        // Si le cluster est vide, on ne fait rien
        if(groupe.isEmpty()) return;

        // Calcul du barycentre
        int red = 0;
        int green = 0;
        int blue = 0;
        for (Point p : groupe) {
            red += p.getRed();
            green += p.getGreen();
            blue += p.getBlue();
        }
        red /= groupe.size();
        green /= groupe.size();
        blue /= groupe.size();

        // Mise a jour du centre
        centroid = new Point(red, green, blue, -1, -1);
    }

    /**
     * Vide le cluster
     */
    public void resetPoint() {
        groupe.clear();
    }

    /**
     * Retourne la liste des points du cluster
     * @return la liste des points du cluster
     */
    public List<Point> getGroupe() {
        return groupe;
    }
}

