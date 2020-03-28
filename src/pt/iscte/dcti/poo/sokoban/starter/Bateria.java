package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Bateria extends SokobanObject implements ActiveObject {
    private int charge = 100;

    public Bateria(Point2D position, String imageName) {
        super(2, position, imageName);
        SokobanGame.getInstance().objects.add(this);
    }

    public int getCharge() {
        return this.charge;
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            ((Player)object).addEnergy(this.getCharge());
            SokobanGame.getInstance().objects.remove(this);
            ImageMatrixGUI.getInstance().removeImage(this);
        }
    }
}
