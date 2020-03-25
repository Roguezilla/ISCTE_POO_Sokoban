package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Bateria extends SokobanObject {
    private Point2D position;

    public Bateria(Point2D position){
        this.position = position;
        SokobanGame.interactables.add(this);
    }

    @Override
    public String getName() {
        return "Bateria";
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
