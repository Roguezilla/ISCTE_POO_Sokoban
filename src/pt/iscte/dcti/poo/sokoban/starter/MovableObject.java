package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.util.List;

//extended by all movable objects
public abstract class MovableObject extends SokobanObject {
    MovableObject(int layer) {
        super(layer);
    }

    //only Player overrides this function
    public boolean canMoveTo(Point2D position) {
        if (!(position.getX() >= 0 && position.getX() < 10 && position.getY() >= 0 && position.getY() < 10)) return false;
        if (SokobanGame.selectObject(sokobanObject -> sokobanObject instanceof Parede && sokobanObject.isAt(position)) != null) return false;
        if (SokobanGame.selectObject(sokobanObject -> sokobanObject.isAt(position) && (sokobanObject instanceof MovableObject || sokobanObject instanceof Bateria)) != null) return false;
        return true;
    }

    public void move(Direction direction) {
        Point2D newPosition = position.plus(direction.asVector());
        if (!this.canMoveTo(newPosition)) return;

        this.position = newPosition;

        List<SokobanObject> possibleCollisions = SokobanGame.selectObjects(sokobanObject -> sokobanObject.isAt(newPosition) && sokobanObject instanceof ActiveObject);
        for (SokobanObject possibleCollision : possibleCollisions) {
            this.interactWith(possibleCollision);
        }
    }
}
