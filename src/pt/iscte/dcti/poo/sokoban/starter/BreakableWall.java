package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;

public class BreakableWall extends SokobanObject implements StaticObject, ActiveObject {
    public BreakableWall(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            if (((Player)object).hasHammer()) {
                Sokoban.getInstance().objects.remove(this);
                ImageMatrixGUI.getInstance().removeImage(this);
            }
        }
    }

    @Override
    public boolean isBreakable() {
        return Sokoban.getInstance().getPlayer().hasHammer();
    }
}
