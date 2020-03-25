package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Alvo extends SokobanObject {
    private Point2D position;
    private int state = 0;

    public Alvo(Point2D position){
        this.position = position;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String getName() {
        return "Alvo";
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
