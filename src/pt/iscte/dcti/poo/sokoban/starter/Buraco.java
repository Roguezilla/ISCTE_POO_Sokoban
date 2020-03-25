package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends SokobanObject {
    private Point2D position;

    public Buraco(Point2D position){
        this.position = position;
        SokobanGame.interactables.add(this);
    }

    @Override
    public String getName() {
        return "Buraco";
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public int getLayer() {
        return 2;
    }
}
