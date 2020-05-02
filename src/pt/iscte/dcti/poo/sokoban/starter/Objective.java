package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Objective extends SokobanObject implements ActiveObject {
    private int state = 0;

    public Objective(Point2D position, String imageName) {
        super(2, position, imageName);
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
            //instead of writing a ton of code to check if a box was moved from an objective to change states
            //we can just set an objective's state to 0 when the player passes over it, cuz that always happens
            //when the player tries moving a box from an objective
            this.setState(0);
        } else if (object instanceof Box) {
            this.setState(1);
        }
    }
}
