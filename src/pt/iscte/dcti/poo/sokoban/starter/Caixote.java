package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Caixote extends MovableObject implements ActiveObject {
    public Caixote(Point2D position, String imageName) {
        super(3, position, imageName);
        SokobanGame.objects.add(this);
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.move(((Player)object).getDirection());
        } else if (object instanceof Alvo) {
            ((Alvo)object).setState(1);
        } else if (object instanceof Buraco) {
            System.exit(0);
        }
    }
}