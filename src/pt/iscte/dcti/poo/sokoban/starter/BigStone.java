package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class BigStone extends SokobanObject implements MovableObject {
    private Point2D position;
    private boolean isInHole = true;

    public BigStone(Point2D position){
        this.position = position;
        SokobanGame.interactables.add(this);
    }

    @Override
    public String getName() {
        return "BigStone";
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    public boolean isInHole() {
        return this.isInHole;
    }

    @Override
    public void move(Direction direction) {
        Point2D newPosition = this.position.plus(direction.asVector());
        if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
            //did the crate collide with a wall? if so, dont move
            if (SokobanGame.walls.stream().filter(point2D -> point2D.equals(newPosition)).findFirst().orElse(null) != null) return;
            //did the stone collide with another movable object? if so, dont move
            if (SokobanGame.interactables.stream()
                    .filter(object -> object instanceof MovableObject)
                    .filter(caixote -> newPosition.equals(caixote.getPosition())).findFirst().orElse(null) != null) return;

            this.position = newPosition;

            //did the stone collide with a hole? if so, make the stone unmovable and remove the hole from the game
            Buraco holeCollision = (Buraco)SokobanGame.interactables.stream().filter(object -> object instanceof Buraco).filter(buraco -> buraco.getPosition().equals(position)).findFirst().orElse(null);
            if (holeCollision != null) {
                this.isInHole = false;
                ImageMatrixGUI.getInstance().removeImage(holeCollision);
                SokobanGame.interactables.remove(holeCollision);
            }
        }

        ImageMatrixGUI.getInstance().update();
    }
}
