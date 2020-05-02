package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

//every object in the game extends this class
public abstract class SokobanObject implements ImageTile {
    Point2D position;
    String imageName;
    int layer;

    SokobanObject(int layer, Point2D position, String imageName) {
        this.layer = layer;
        this.position = position;
        this.imageName = imageName;

        Sokoban.getInstance().objects.add(this);
    }

    @Override
    public int getLayer() {
        return this.layer;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public String getName() {
        return this.imageName;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    //only exists because its easier to type just isAt(pos)
    boolean isAt(Point2D position) {
        return this.getPosition().equals(position);
    }
}
