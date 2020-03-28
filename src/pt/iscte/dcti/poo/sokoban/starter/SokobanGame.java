package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SokobanGame implements Observer {
	//stores the instance
	private static SokobanGame INSTANCE = null;
	//a list of every object in the game
 	public List<SokobanObject> objects = new ArrayList<>();
 	//stores the instance of the player
	private Player player;
	//stores current level, incremented if all objectives are met
	private int level = 0;
	//stores game state, prevents writing score after completeting the last level
	private boolean gameWon = false;

	public static SokobanGame getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SokobanGame();
		}

		return INSTANCE;
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

	//handles multiple collisions at once
	public static List<SokobanObject> selectObjects(Predicate<SokobanObject> predicate) {
		return SokobanGame.getInstance().objects.stream().filter(predicate).collect(Collectors.toList());
	}

	//handles single collisions, used for very specific collisions
	public static SokobanObject selectObject(Predicate<SokobanObject> predicate) {
		return SokobanGame.getInstance().objects.stream().filter(predicate).findFirst().orElse(null);
	}

	//builds the first level and sets the appropriate status message
	public void build() {
		buildLevel(this.level);
		ImageMatrixGUI.getInstance().setStatusMessage("Level: " + this.level + " Moves: " + this.player.getTotalMoves() + " Energy: " + this.player.getEnergy());
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
		//did we beat the current level? if so, advance to the next one(if they exist) and save score. also dont call this whole chunk of we won the game
		if (!this.gameWon && this.objects.stream().filter(sokobanObject -> sokobanObject instanceof Alvo).mapToInt(alvo -> ((Alvo)alvo).getState()).sum() == this.objects.stream().filter(sokobanObject -> sokobanObject instanceof Alvo).count() && this.level <= (new File("levels").listFiles().length - 1)) {
			//handles score saving
			{
				//create a score folder if it doesnt exist
				File score_folder = new File("level_scores");
				score_folder.mkdir();
				//create a score file for each level if it doesnt exist already, file.createNewFile() handles this for us
				for(File file : new File("levels").listFiles()) {
					try {
						new File(score_folder.getPath() + "/" + file.getName()).createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//the function for the score system is score = 10000 / moves
				int score = 10000 / this.player.getTotalMoves();
				System.out.println(this.player.getTotalMoves() + " " + score);
				try {
					FileWriter fileWriter = new FileWriter(new File("level_scores/" + "level" + this.level + ".txt"), true);
					fileWriter.write("\n" + score);
					fileWriter.close();
				} catch (IOException e) {
					System.out.println("Level score file not found, should not happen due to class constructor.");
				}
			}
			//did we complete all levels?
			if (this.level == (new File("levels").listFiles().length - 1)) {
				ImageMatrixGUI.getInstance().setName("Victory!");
				this.gameWon = true;
			}
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
