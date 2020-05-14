package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Hole extends SokobanObject implements ActiveObject {
    public Hole(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        //all objects that can interact with a hole are instances of MovableObject, so the cast is safe
        ((MovableObject)object).interactWithHole(this);
    }
}
