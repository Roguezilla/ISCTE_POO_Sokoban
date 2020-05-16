package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Sokoban implements Observer {
	//stores the instance
	private static Sokoban INSTANCE = null;
	//a list of every object in the game
 	private List<SokobanObject> objects = new ArrayList<>();
 	//stores the instance of the player
	private Player player;
	//stores player name
	private String playerName = "";
	//stores current level, incremented if all objectives are met
	private int level = 0;
	//stores game state, prevents writing score after completeting the last level
	private boolean gameWon = false;

	public static Sokoban getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Sokoban();
			//ask for input until the user inputs a non emptry name
			while (INSTANCE.playerName.isEmpty()) {
				INSTANCE.playerName = JOptionPane.showInputDialog("Player name: ");
			}
			//intialize the first level
			try {
				INSTANCE.buildLevel(INSTANCE.level);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ImageMatrixGUI.getInstance().setStatusMessage("Level: " + INSTANCE.level + " Moves: " + INSTANCE.player.getTotalMoves() + " Energy: " + INSTANCE.player.getEnergy());
		}

		return INSTANCE;
	}

	public Player getPlayer() {
		return this.player;
	}

	public List<SokobanObject> getObjects() {
		return this.objects;
	}

	//better than always calling 2 functions
	public static void disposeObject(SokobanObject object) {
		Sokoban.getInstance().objects.remove(object);
		ImageMatrixGUI.getInstance().removeImage(object);
	}

	private ImageTile charToImageTile(char currentChar, int x, int y) {
		switch (currentChar) {
			case '#': {
				return new Wall(new Point2D(x, y), "Parede");
			} case 'X': {
				return new Objective(new Point2D(x, y), "Alvo");
			} case 'C': {
				return new Box(new Point2D(x, y), "Caixote");
			} case 'O': {
				return new Hole(new Point2D(x, y), "Buraco");
			} case 'p': {
				return new SmallStone(new Point2D(x, y), "SmallStone");
			} case 'P': {
				return new BigStone(new Point2D(x, y), "BigStone");
			} case 'b': {
				return new Battery(new Point2D(x, y), "Bateria");
			} case 'g': {
				return new Ice(new Point2D(x, y), "Gelo");
			} case 'm': {
				return new Hammer(new Point2D(x, y), "Martelo");
			} case '%': {
				return new BreakableWall(new Point2D(x, y), "Parede_Partida");
			} case 't': {
				//for the first 't' found we just return a portal without doing anything special with it
				if (this.selectObject(sokobanObject -> sokobanObject instanceof Portal) == null) {
					return new Portal(new Point2D(x, y), "Portal_Azul");
				} else {
					//for second 't' we create a portal and link it to the previously created one
					Portal portal = new Portal(new Point2D(x, y), "Portal_Verde");
					portal.link((Portal)this.selectObject(sokobanObject -> sokobanObject instanceof Portal));
					return portal;
				}
			} case 'E': {
				this.player = new Player(new Point2D(x, y));
				return this.player;
			} default: {
				return new Floor(new Point2D(x, y), "Chao");
			}
		}
	}

	private void buildLevel(int level) throws FileNotFoundException {
		List<ImageTile> tileSet = new ArrayList<>();

		//fill the screen with floor tiles first so that we dont have to worry about missing floor under movable objects after we move/remove them
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tileSet.add(new Floor(new Point2D(i, j), "Chao"));
			}
		}

		//read chars from file and transform them into their equivalent objects
		Scanner scanner = new Scanner(new File("levels/level" + level + ".txt"));
		int y = 0;
		while (scanner.hasNextLine()) {
			char[] next = scanner.nextLine().toCharArray();
			for (int i = 0; i < next.length; i++) {
				tileSet.add(this.charToImageTile(next[i], i, y));
			}
			y++;
		}

		if (this.objects.stream().filter(sokobanObject -> sokobanObject instanceof Objective).count() != this.objects.stream().filter(sokobanObject -> sokobanObject instanceof Box).count()) {
			throw new IllegalArgumentException("Number of boxes and objectives don't match.");
		}

		ImageMatrixGUI.getInstance().addImages(tileSet);
	}

	//handles multiple collisions at once
	public List<SokobanObject> selectObjects(Predicate<SokobanObject> predicate) {
		return Sokoban.getInstance().getObjects().stream().filter(predicate).collect(Collectors.toList());
	}

	//handles single collisions, used for very specific collisions
	public SokobanObject selectObject(Predicate<SokobanObject> predicate) {
		return Sokoban.getInstance().getObjects().stream().filter(predicate).findFirst().orElse(null);
	}

	//clears the level images, objects and player
	public void clearGameData() {
		ImageMatrixGUI.getInstance().clearImages();
		this.objects.clear();
		this.player = null;
	}

	private void saveScore() throws IOException {
		//create a score folder if it doesnt exist
		File score_folder = new File("level_scores");
		score_folder.mkdir();

		//create a score file for each level if it doesnt exist already, file.createNewFile() handles this for us
		for(File file : new File("levels").listFiles()) {
			new File(score_folder.getPath() + "/" + file.getName()).createNewFile();
		}

		//the function for the score system is score = 10000 / moves
		int score = 10000 / this.player.getTotalMoves();
		System.out.println("Moves: " + this.player.getTotalMoves() + " Score: " + score);

		//storing scores as "playername score" allows easy score file parsing if needed in the future if needed
		FileWriter fileWriter = new FileWriter(new File(score_folder.getPath() + "/" + "level" + this.level + ".txt"), true);
		fileWriter.write(this.playerName + " " + score + "\n");
		fileWriter.close();
	}

	@Override
	public void update(Observed arg0) {
		int lastKeyPressed = ((ImageMatrixGUI)arg0).keyPressed();
		if (Direction.isDirection(lastKeyPressed)) {
			this.player.move(Direction.directionFor(lastKeyPressed));
		}

		//avoid checking objectives if we already beat all levels
		if (!this.gameWon) {
			//did we beat the current level? if so, advance to the next one and save score.
			File[] levels = new File("levels").listFiles();
			if (Objective.areAllObjectivesActive() && this.level <= levels.length - 1) {
				//handles score saving
				try {
					this.saveScore();
				} catch (IOException e) {
					e.printStackTrace();
				}

				//did we complete all levels?
				if (this.level == levels.length - 1) {
					ImageMatrixGUI.getInstance().setName("Victory!");
					this.gameWon = true;
				}

				//advance level if we arent on the last level
				if (this.level < levels.length - 1) {
					this.clearGameData();
					try {
						this.buildLevel(++this.level);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}

		ImageMatrixGUI.getInstance().setStatusMessage("Level: " + this.level + " Moves: " + this.player.getTotalMoves() + " Energy: " + this.player.getEnergy());
		ImageMatrixGUI.getInstance().update();
	}
}
