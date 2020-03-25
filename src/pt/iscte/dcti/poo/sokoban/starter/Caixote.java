package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Caixote extends SokobanObject implements MovableObject {
    private Point2D position;

    public Caixote(Point2D position){
        this.position = position;
        SokobanGame.interactables.add(this);
    }

    @Override
    public String getName() {
        return "Caixote";
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public int getLayer() {
        return 4;
    }

    @Override
    public void move(Direction direction) {
        Point2D newPosition = this.position.plus(direction.asVector());
        if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
            //did the crate collide with a wall? if so, dont move
            if (SokobanGame.walls.stream().filter(point2D -> point2D.equals(newPosition)).findFirst().orElse(null) != null) return;
            //did the crate collide with another movable object? if so, dont move
            if (SokobanGame.interactables.stream()
                    .filter(object -> object instanceof MovableObject)
                    .filter(caixote -> newPosition.equals(caixote.getPosition())).findFirst().orElse(null) != null) return;

            //at this point we collided with the box, so we are gonna move it. now we just check if the box was on an objective and if so we set that objective's state to false
            Alvo moveOutOfObjectiveCollision = SokobanGame.objectives.stream().filter(alvo -> alvo.getPosition().equals(this.position)).findFirst().orElse(null);
            if (moveOutOfObjectiveCollision != null) {
                moveOutOfObjectiveCollision.setState(0);
            }

            this.position = newPosition;

            //at this point the box is already moved, so we check if its on an objective and if we so we set that objective's state to true
            Alvo moveIntoObjectiveCollision = SokobanGame.objectives.stream().filter(alvo -> alvo.getPosition().equals(this.position)).findFirst().orElse(null);
            if (moveIntoObjectiveCollision != null) {
                moveIntoObjectiveCollision.setState(1);
            }

            //did the box collide with a hole? if so, remove it from the game
            Buraco holeCollision = (Buraco)SokobanGame.interactables.stream().filter(object -> object instanceof Buraco).filter(buraco -> buraco.getPosition().equals(this.position)).findFirst().orElse(null);
            if (holeCollision != null) {
                ImageMatrixGUI.getInstance().removeImage(this);
                SokobanGame.interactables.remove(this);
                System.exit(0);
            }
        }

        ImageMatrixGUI.getInstance().update();
    }
}
