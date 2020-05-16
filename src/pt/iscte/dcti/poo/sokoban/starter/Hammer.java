package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Hammer extends SokobanObject implements ActiveObject, PickupableObject {
    public Hammer(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            ((Player)object).addAbility(this);
            Sokoban.disposeObject(this);
        }
    }
}
