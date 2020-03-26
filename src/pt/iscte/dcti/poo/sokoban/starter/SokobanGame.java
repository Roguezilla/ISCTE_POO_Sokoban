package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SokobanGame implements Observer {
	//a list of every object in the game
 	public static List<SokobanObject> objects = new ArrayList<>();
 	//stores the instance of the player
	private Player player;
	//stores current level, incremented if all objectives are met
	private int level = 0;
	//store gamestate, usused as of right now
	private boolean won = false;

	//needs to be changed so i can turn this into a singleton
	public SokobanGame() {
		//build first level and set the proper status message
		buildLevel(this.level);
		ImageMatrixGUI.getInstance().setStatusMessage("Level: " + this.level + " Moves: " + this.player.getTotalMoves() + " Energy: " + this.player.getEnergy());
	}

	private void buildLevel(int level) {
		try {
			List<ImageTile> tileSet = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					tileSet.add(new Chao(new Point2D(i, j), "Chao"));
				}
			}

			Scanner scanner = new Scanner(new File("levels/level" + level + ".txt"));
			int y = 0;
			while (scanner.hasNextLine()) {
				char[] next = scanner.nextLine().toCharArray();
				for (int i = 0; i < next.length; i++) {
					switch (next[i]) {
						case '#': {
							tileSet.add(new Parede(new Point2D(i, y), "Parede"));
							break;
						}
						case 'X': {
							tileSet.add(new Alvo(new Point2D(i, y), "Alvo"));
							break;
						} case 'C': {
							tileSet.add(new Caixote(new Point2D(i, y), "Caixote"));
							break;
						} case 'O': {
							tileSet.add(new Buraco(new Point2D(i, y), "Buraco"));
							break;
						} case 'p': {
							tileSet.add(new SmallStone(new Point2D(i, y), "SmallStone"));
							break;
						} case 'P': {
							tileSet.add(new BigStone(new Point2D(i, y), "BigStone"));
							break;
						} case 'b': {
							tileSet.add(new Bateria(new Point2D(i, y), "Bateria"));
							break;
						} case 'E': {
							this.player = new Player(new Point2D(i, y));
							tileSet.add(player);
							break;
						} default: {
							tileSet.add(new Chao(new Point2D(i, y), "Chao"));
							break;
						}
					}
				}
				y++;
			}

			ImageMatrixGUI.getInstance().addImages(tileSet);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static List<SokobanObject> selectObjects(Predicate<SokobanObject> predicate) {
		return objects.stream().filter(predicate).collect(Collectors.toList());
	}

	public static SokobanObject selectObject(Predicate<SokobanObject> predicate) {
		return objects.stream().filter(predicate).findFirst().orElse(null);
	}

	//clears the level images, collidable objects and objectives
	public void clearGameData() {
		ImageMatrixGUI.getInstance().clearImages();
		this.objects.clear();
		this.player = null;
	}

	@Override
	public void update(Observed arg0) {
		int lastKeyPressed = ((ImageMatrixGUI)arg0).keyPressed();
		//did we beat the current level? if so, advance to the next one(if they exist) and save score.
		if (objects.stream().filter(sokobanObject -> sokobanObject instanceof Alvo).mapToInt(alvo -> ((Alvo)alvo).getState()).sum() == objects.stream().filter(sokobanObject -> sokobanObject instanceof Alvo).count() && this.level <= (new File("levels").listFiles().length - 1)) {
			//prevents the game from advancing to non existent levels
			if (this.level < (new File("levels").listFiles().length - 1)) {
				this.clearGameData();
				this.buildLevel(++this.level);
				ImageMatrixGUI.getInstance().update();
			}
		}
		if (Direction.isDirection(lastKeyPressed)) {
			this.player.move(Direction.directionFor(lastKeyPressed));
		}

		ImageMatrixGUI.getInstance().setStatusMessage("Level: " + this.level + " Moves: " + this.player.getTotalMoves() + " Energy: " + this.player.getEnergy());
		ImageMatrixGUI.getInstance().update();
	}
}
