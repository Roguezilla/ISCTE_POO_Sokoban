package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.util.HashMap;
import java.util.List;

public class Player extends MovableObject {
	private HashMap<Direction, String> facingImage = new HashMap<>() {
		{
			put(Direction.DOWN, "Empilhadora_D");
			put(Direction.UP, "Empilhadora_U");
			put(Direction.LEFT, "Empilhadora_L");
			put(Direction.RIGHT, "Empilhadora_R");
		}
	};
	private int energy = 100;
	private int totalMoves = 0;
	private Direction direction;
	
	public Player(Point2D position) {
		super(3);
		this.position = position;
		this.imageName = facingImage.get(Direction.UP);
		SokobanGame.objects.add(this);
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getEnergy() {
		return this.energy;
	}

	public void addEnergy(int amount) {
		this.energy += amount;
	}

	public int getTotalMoves() {
		return this.totalMoves;
	}

	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public boolean canMoveTo(Point2D position) {
		//are we in bounds? walls make this kinda useless, but you never know
		if (!(position.getX() >=0 && position.getX() < 10 && position.getY() >= 0 && position.getY() < 10)) return false;
		//did we hit a wall? if so, dont move
		if (SokobanGame.selectObject(sokobanObject -> sokobanObject instanceof Parede && sokobanObject.isAt(position)) != null) return false;
		//did we hit a stone thats in a hole? if so, dont move
		if (SokobanGame.selectObject(sokobanObject -> sokobanObject instanceof BigStone && sokobanObject.isAt(position) && ((BigStone)sokobanObject).isInHole()) != null) return false;
		//did we hit a movable object? if so, can that movable object be moved?
		MovableObject possibleCollision = (MovableObject)SokobanGame.selectObject(sokobanObject -> sokobanObject.isAt(position) && sokobanObject instanceof MovableObject);
		if (possibleCollision != null) {
			return possibleCollision.canMoveTo(position.plus(this.getDirection().asVector()));
		}
		return true;
	}

	@Override
	public void move(Direction direction) {
		this.direction = direction;

		Point2D newPosition = position.plus(this.direction.asVector());
		if (!this.canMoveTo(newPosition)) return;

		this.position = newPosition;

		List<SokobanObject> possibleCollisions = SokobanGame.selectObjects(sokobanObject -> sokobanObject.getPosition().equals(newPosition) && sokobanObject instanceof ActiveObject);
		for (SokobanObject possibleCollision : possibleCollisions) {
			possibleCollision.interactWith(this);
		}

		this.totalMoves++;
		this.energy--;

		this.setImageName(this.facingImage.get(direction));
	}
}
