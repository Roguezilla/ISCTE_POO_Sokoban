package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends SokobanObject implements ActiveObject {
    public Buraco(Point2D position, String imageName) {
        super(2, position, imageName);
        SokobanGame.objects.add(this);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            System.exit(0);
        }
    }
}
