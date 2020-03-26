package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Parede extends SokobanObject {
    public Parede(Point2D position, String imageName) {
        super(2);
        this.position = position;
        this.imageName = imageName;
        SokobanGame.objects.add(this);
    }
}
