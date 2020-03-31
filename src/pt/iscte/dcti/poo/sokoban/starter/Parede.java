package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Parede extends SokobanObject implements ObstacleObject {
    public Parede(Point2D position, String imageName) {
        super(2, position, imageName);
        SokobanGame.getInstance().objects.add(this);
    }
}
