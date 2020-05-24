package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Ice extends SokobanObject implements ActiveObject {
    public Ice(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    @Override
    public void interactWith(SokobanObject object) {
        //anything that can touch ice is a movable object so this cast is safe
        MovableObject movableObject = (MovableObject)object;
        //objects are moved in the direction the player is moving
        Direction playerDir = Sokoban.getInstance().getPlayer().getDirection();
        /*
        2 bugs are fixed here:
        1. fixes the player and the movable object being moved to the same spot after the player tries moving it from when its on ice due to a previous collision
        2. fixes objects getting moved twice
        */
        SokobanObject onIce = Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(this.getPosition()) && sokobanObject instanceof MovableObject);
        if (onIce != null) {
            if (movableObject instanceof Player) {
                movableObject.move(playerDir);
            }
            ((MovableObject)onIce).move(playerDir);
        } else {
            movableObject.move(playerDir);
        }
    }
}
