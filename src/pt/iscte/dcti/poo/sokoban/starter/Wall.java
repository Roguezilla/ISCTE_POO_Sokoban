package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Wall extends SokobanObject implements StaticObject {
    public Wall(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    @Override
    public boolean isBreakable() {
        return false;
    }
}
