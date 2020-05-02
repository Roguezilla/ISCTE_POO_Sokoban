package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Hole extends SokobanObject implements ActiveObject {
    public Hole(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            ImageMatrixGUI.getInstance().dispose();
            System.exit(0);
        } else if (object instanceof Box) {
            ImageMatrixGUI.getInstance().dispose();
            System.exit(0);
        } else if (object instanceof BigStone) {
            ((BigStone)object).setMovability(false);
            Sokoban.getInstance().objects.remove(this);
            ImageMatrixGUI.getInstance().removeImage(this);
        } else if (object instanceof SmallStone) {
            Sokoban.getInstance().objects.remove(object);
            ImageMatrixGUI.getInstance().removeImage(object);
        }
    }
}
