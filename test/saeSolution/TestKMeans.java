package saeSolution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestKMeans {

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        BufferedImage image = null;
        // Lecture de l'image
        try {
            image = ImageIO.read(new File("resources/animaux/ours.png"));
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

        int nbRepet = 10;

        for (int i = 1; i <= 128; i *= 2) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < nbRepet; j++) {
                KMeans kmeans = new KMeans(points, i);
                kmeans.run();
            }
            long end = System.currentTimeMillis();
            System.out.println(i + ", " + (end-begin)/nbRepet);
        }



    }
}
