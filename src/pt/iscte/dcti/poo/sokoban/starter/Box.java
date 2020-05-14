package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Box extends MovableObject implements ActiveObject {
    public Box(Point2D position, String imageName) {
        super(3, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.move(((Player)object).getDirection());
        }
    }

    @Override
    void interactWithHole(Hole hole) {
        Sokoban.disposeObject(this);
    }
}
