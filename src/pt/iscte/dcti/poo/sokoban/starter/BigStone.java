package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class BigStone extends MovableObject implements ActiveObject {
    private boolean isInHole = false;

    public BigStone(Point2D position, String imageName) {
        super(3, position, imageName);
        SokobanGame.getInstance().objects.add(this);
    }

    public boolean isInHole() {
        return this.isInHole;
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.move(((Player)object).getDirection());
        } else if (object instanceof Buraco) {
            this.isInHole = true;
            SokobanGame.getInstance().objects.remove(object);
            ImageMatrixGUI.getInstance().removeImage(object);
        }
    }
}
