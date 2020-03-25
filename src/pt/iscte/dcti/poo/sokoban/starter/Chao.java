package pt.iscte.dcti.poo.sokoban.starter;
import pt.iul.ista.poo.utils.Point2D;

public class Chao extends SokobanObject {
	private Point2D position;
	
	public Chao(Point2D position){
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "Chao";
	}

	@Override
	public Point2D getPosition() {
		return this.position;
	}

	@Override
	public int getLayer() {
		return 2;
	}

}
