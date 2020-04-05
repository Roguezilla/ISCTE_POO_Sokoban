package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Box extends MovableObject implements ActiveObject {
    public Box(Point2D position, String imageName) {
        super(3, position, imageName);
        Sokoban.getInstance().objects.add(this);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.move(((Player)object).getDirection());
        } else if (object instanceof Objective) {
            ((Objective)object).setState(1);
        } else if (object instanceof Hole) {
            System.exit(0);
        }
    }
}