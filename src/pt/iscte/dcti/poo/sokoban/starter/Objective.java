package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Objective extends SokobanObject implements ActiveObject {
    private int state = 0;

    public Objective(Point2D position, String imageName) {
        super(2, position, imageName);
        Sokoban.getInstance().objects.add(this);
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (object instanceof Player) {
            this.setState(0);
        }
    }
}
