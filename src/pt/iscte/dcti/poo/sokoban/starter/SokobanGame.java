package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SokobanGame implements Observer {
	//we only need the position of the walls as there aren't interactable objects
 	public static List<Point2D> walls = new ArrayList<>();
 	/*
	* we need the instance of each interactable object, because we need their functions and delete them if needed
	* each object in the game extends SokobanObject, which make it easier on us by removing the need to create
	* a list for each object. movable objects implement MovableObject which makes them easy to filter in the list
 	*/
 	public static List<SokobanObject> interactables = new ArrayList<>();
 	//objectives are in a separate list just for the ease of accessing them
 	public static List<Alvo> objectives = new ArrayList<>();

 	//stores the instance of the player
	private Player player;
	//stores current level, incremented if all objectives are met
	private int lvl = 0;
	
	public SokobanGame() throws FileNotFoundException {
		buildLevel(this.lvl);
		ImageMatrixGUI.getInstance().setStatusMessage("Level: " + this.lvl + " Moves: " + this.player.getTotalMoves() + " Energy: " + this.player.getEnergy());
	}

	private void buildLevel(int level) throws FileNotFoundException {
		List<ImageTile> currentTileSet = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				currentTileSet.add(new Chao(new Point2D(i, j)));
			}
		}

		Scanner scanner = new Scanner(new File("levels/level" + level + ".txt"));
		int y = 0;
		while (scanner.hasNextLine()) {
			char[] next = scanner.nextLine().toCharArray();
			for (int i = 0; i < next.length; i++) {
				if (next[i] == '#'){
					currentTileSet.add(new Parede(new Point2D(i, y)));
				} else if (next[i] == 'X'){
					Alvo alvo = new Alvo(new Point2D(i, y));
					currentTileSet.add(alvo);
					objectives.add(alvo);
				} else if (next[i] == 'C'){
					currentTileSet.add(new Caixote(new Point2D(i, y)));
				} else if (next[i] == 'O'){
					currentTileSet.add(new Buraco(new Point2D(i, y)));
				} else if (next[i] == 'p'){
					currentTileSet.add(new SmallStone(new Point2D(i, y)));
				} else if (next[i] == 'P'){
					currentTileSet.add(new BigStone(new Point2D(i, y)));
				} else if (next[i] == 'b'){
					currentTileSet.add(new Bateria(new Point2D(i, y)));
				} else if (next[i] == 'E') {
					this.player = new Player(new Point2D(i, y));
					currentTileSet.add(player);
				}
			}
			y++;
		}

		ImageMatrixGUI.getInstance().addImages(currentTileSet);
	}

	//clears the level images, collidable objects and objectives
	public void clearGameData() {
		ImageMatrixGUI.getInstance().clearImages();
		walls.clear();
		interactables.clear();
		objectives.clear();
	}

	@Override
	public void update(Observed arg0) {
		int lastKeyPressed = ((ImageMatrixGUI)arg0).keyPressed();
		//did we beat the final level?
		if (objectives.stream().mapToInt(Alvo::getState).sum() == objectives.size() && this.lvl == new File("levels").listFiles().length - 1) {
			ImageMatrixGUI.getInstance().setName("Victory!");
		}
		//did we beat the current level? also prevents the game from looking for non existent levels
		if (objectives.stream().mapToInt(Alvo::getState).sum() == objectives.size() && this.lvl < new File("levels").listFiles().length - 1) {
			this.clearGameData();
			try {
				this.buildLevel(++this.lvl);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ImageMatrixGUI.getInstance().update();
		}
		if (lastKeyPressed >= KeyEvent.VK_LEFT && lastKeyPressed <= KeyEvent.VK_DOWN) {
			this.player.move(Direction.directionFor(lastKeyPressed));
		}

		ImageMatrixGUI.getInstance().setStatusMessage("Level: " + this.lvl + " Moves: " + this.player.getTotalMoves() + " Energy: " + this.player.getEnergy());
	}
}
