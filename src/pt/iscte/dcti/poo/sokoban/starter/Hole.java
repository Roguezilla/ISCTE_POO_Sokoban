package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Hole extends SokobanObject implements ActiveObject {
    public Hole(Point2D position, String imageName) {
        super(2, position, imageName);
        Sokoban.getInstance().objects.add(this);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            ImageMatrixGUI.getInstance().dispose();
            System.exit(0);
        }
    }
}
