package pt.iscte.dcti.poo.sokoban.starter;

import java.util.HashMap;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Player extends SokobanObject implements MovableObject {
	private HashMap<Direction, String> facingImage = new HashMap<>() {
		{
			put(Direction.DOWN, "Empilhadora_D");
			put(Direction.UP, "Empilhadora_U");
			put(Direction.LEFT, "Empilhadora_L");
			put(Direction.RIGHT, "Empilhadora_R");
		}
	};
	private Point2D position;
	private String imageName;
	private int energy = 100;
	private int totalMoves = 0;
	
	public Player(Point2D initialPosition){
		position = initialPosition;
		imageName = facingImage.get(Direction.UP);
	}
	
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return this.position;
	}

	@Override
	public int getLayer() {
		return 3;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int getTotalMoves() {
		return this.totalMoves;
	}

	@Override
	public void move(Direction direction) {
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX() >=0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
			//did we collide wiht a wall? if so, dont move
			if (SokobanGame.walls.stream().filter(point2D -> point2D.equals(newPosition)).findFirst().orElse(null) != null) return;
			//did we collide with a movable object?
			SokobanObject possibleCollion = SokobanGame.interactables.stream()
					.filter(object -> object instanceof MovableObject)
					.filter(caixote -> caixote.getPosition().equals(newPosition)).findFirst().orElse(null);
			if (possibleCollion != null) {
				//did we collide with a big stone thats in a hole? if so, we dont move
				if (possibleCollion instanceof BigStone && !((BigStone) possibleCollion).isInHole()) return;

				possibleCollion.move(direction);
				//we collided with a movable object, but does that object collide with another object like a wall? if so, dont move
				if (newPosition.equals(possibleCollion.getPosition())) return;
			}
			this.position = newPosition;

			//did the box collide with a battery? if so, add energy remove it from the game
			SokobanObject possibleBatteryCollision = SokobanGame.interactables.stream().filter(object -> object instanceof Bateria).filter(caixote -> caixote.getPosition().equals(position)).findFirst().orElse(null);
			if (possibleBatteryCollision != null) {
				this.energy += 1000;
				ImageMatrixGUI.getInstance().removeImage(possibleBatteryCollision);
				SokobanGame.interactables.remove(possibleBatteryCollision);
			}
		}

		this.setImageName(this.facingImage.get(direction));

		this.totalMoves++;
		this.energy -= 10;

		if (this.energy < 0) {
			//System.exit(0);
		}

		ImageMatrixGUI.getInstance().update();
	}
}
