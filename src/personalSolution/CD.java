package personalSolution;

import java.awt.*;

/**
 * CD : Couleur et Distances
 */
public class CD {
    private Color color;
    private long distances;

    public CD(Color color, long distances){
        this.color = color;
        this.distances = distances;
    }

    public Color getColor() {
        return color;
    }

    public long getDistances() {
        return distances;
    }

    @Override
    public String toString() {
        return "Color-Red:"+color.getRed()+" Green:"+color.getGreen()+" Blue:"+color.getBlue()+" distances:"+distances;
    }
}
