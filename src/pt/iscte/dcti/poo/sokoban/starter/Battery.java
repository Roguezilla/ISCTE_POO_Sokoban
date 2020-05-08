package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Battery extends SokobanObject implements ActiveObject, PickupableObject {
    private final int CHARGE = 100;

    public Battery(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    public int getCharge() {
        return this.CHARGE;
    }

    @Override
    public void interactWith(SokobanObject object) {
        //a "just to be safe" check, other objects shouldnt be able to interact with batteries anyways
        if (object instanceof Player) {
            ((Player)object).addEnergy(this.getCharge());
            Sokoban.getInstance().disposeObject(this);
        }
    }
}
