package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.util.List;

//extended by all movable objects
public abstract class MovableObject extends SokobanObject {
    MovableObject(int layer, Point2D position, String imageName) {
        super(layer, position, imageName);
    }

    public boolean canMoveTo(Point2D position) {
        //is the movable object in bounds? walls make this kinda useless, but you never know
        if (!(position.getX() >= 0 && position.getX() < 10 && position.getY() >= 0 && position.getY() < 10)) return false;
        //did the movable object hit a static object? if so, deny movement
        if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject instanceof StaticObject && sokobanObject.isAt(position)) != null) return false;
        //did the movable object hit another movable object? if so, deny movement
        if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(position) && sokobanObject instanceof MovableObject) != null) return false;
        //did the movable object hit an object the player can pick up? if so, deny movement
        if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(position) && sokobanObject instanceof PickupableObject) != null) return false;

        return true;
    }

    public void move(Direction direction) {
        Point2D newPosition = this.getPosition().plus(direction.asVector());
        if (!this.canMoveTo(newPosition)) return;

        this.setPosition(newPosition);

        //get all the active objects that the movable objects collided with and interact with them
        List<SokobanObject> possibleCollisions = Sokoban.getInstance().selectObjects(sokobanObject -> sokobanObject.isAt(newPosition) && sokobanObject instanceof ActiveObject);
        for (SokobanObject possibleCollision : possibleCollisions) {
            ((ActiveObject)possibleCollision).interactWith(this);
        }
    }

    //this function is abstract because every movable object has a different interaction with the hole,
    //so we can't exactly make a universal function
    abstract void interactWithHole(Hole hole);
}
