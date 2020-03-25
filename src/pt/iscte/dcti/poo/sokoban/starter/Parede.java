package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Parede extends SokobanObject {
    private Point2D position;

    public Parede(Point2D position){
        this.position = position;
        SokobanGame.walls.add(position);
    }

    @Override
    public String getName() {
        return "Parede";
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
