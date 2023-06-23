package saeSolution;

public class Point {

    /**
     * Composante rouge, verte, bleu du point
     */
    private int red, green, blue;

    /**
     * Position du point dans l'image
     */
    private int x, y;

    /**
     * Constructeur
     * @param red composante rouge
     * @param green composante verte
     * @param blue composante bleu
     * @param x position x
     * @param y position y
     */
    public Point(int red, int green, int blue, int x, int y) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Constructeur
     * @param rgb couleur du point
     * @param x position x
     * @param y position y
     */
    public Point(int rgb, int x, int y) {
        this.red = (rgb >> 16) & 0xFF;
        this.green = (rgb >> 8) & 0xFF;
        this.blue = rgb & 0xFF;
        this.x = x;
        this.y = y;
    }

    /**
     * Calcul la distance entre deux points
     * @param p le point a comparer
     * @return la distance entre les deux points
     */
    public int distance(Point p) {
        int red = (int) Math.pow(this.red - p.red, 2);
        int green = (int) Math.pow(this.green - p.green, 2);
        int blue = (int) Math.pow(this.blue - p.blue, 2);
        return red + green + blue;
    }

    /**
     * @return la composante rouge du point
     */
    public int getRed() {
        return red;
    }

    /**
     * @return la composante verte du point
     */
    public int getGreen() {
        return green;
    }

    /**
     * @return la composante bleu du point
     */
    public int getBlue() {
        return blue;
    }

    /**
     * @return la position du point
     */
    public int[] getPosition() {
        return new int[] {x, y};
    }
}
