package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class BigStone extends MovableObject implements ActiveObject, DynamicObject {
    private boolean isInHole = false;

    public BigStone(Point2D position, String imageName) {
        super(3, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.move(((Player)object).getDirection());
        }
    }

    @Override
    public void setMovability(boolean state) {
        this.isInHole = !state;
    }

    @Override
    public boolean canMove() {
        return this.isInHole;
    }

    @Override
    void interactWithHole(SokobanObject object) {
        this.setMovability(false);
        Sokoban.getInstance().disposeObject(object);
    }
}
