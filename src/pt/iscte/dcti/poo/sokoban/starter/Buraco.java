package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends SokobanObject implements ActiveObject {
    public Buraco(Point2D position, String imageName) {
        super(2, position, imageName);
        SokobanGame.getInstance().objects.add(this);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            ImageMatrixGUI.getInstance().dispose();
            //System.exit(0);
        }
    }
}
