package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class SmallStone extends MovableObject implements ActiveObject {
    public SmallStone(Point2D position, String imageName) {
        super(3, position, imageName);
        Sokoban.getInstance().objects.add(this);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.move(((Player)object).getDirection());
        }
    }
}
