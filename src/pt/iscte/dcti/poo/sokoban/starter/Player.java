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
	private boolean hasHammer = false;
	private Direction direction;
	
	public Player(Point2D position) {
		super(3, position, "Empilhadora_U");
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

	public void giveHammer() {
		this.hasHammer = true;
	}

	public boolean hasHammer() {
		return this.hasHammer;
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
		//did we hit a static object? if so, dont move
		if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject instanceof StaticObject && sokobanObject.isAt(position) && !((StaticObject)sokobanObject).isBreakable()) != null) return false;
		//did we hit a dynamic obstacle? if so, we check if that object can move and if not we dont move either
		if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject instanceof DynamicObject && sokobanObject.isAt(position) && ((DynamicObject)sokobanObject).canMove()) != null) return false;
		//did we hit a movable object? if so, can that movable object be moved?
		MovableObject possibleCollision = (MovableObject) Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(position) && sokobanObject instanceof MovableObject);
		if (possibleCollision != null) {
			return possibleCollision.canMoveTo(position.plus(this.getDirection().asVector()));
		}
		return true;
	}

	@Override
	public void move(Direction direction) {
		this.direction = direction;

		Point2D newPosition = position.plus(direction.asVector());
		if (!this.canMoveTo(newPosition)) return;

		this.setPosition(newPosition);;

		//get all the active objects that the player collided with and interact with them. best case example as to explain multiple collision handling here is when the player moves a box from an objective
		List<SokobanObject> possibleCollisions = Sokoban.getInstance().selectObjects(sokobanObject -> sokobanObject.getPosition().equals(newPosition) && sokobanObject instanceof ActiveObject);
		for (SokobanObject possibleCollision : possibleCollisions) {
			((ActiveObject)possibleCollision).interactWith(this);
		}

		//+1 move = -1 energy
		this.totalMoves++;
		this.energy--;

		//set the right image based on the facing direction
		this.setImageName(this.facingImage.get(direction));
	}
}
