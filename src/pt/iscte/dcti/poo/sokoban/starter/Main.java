package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		ImageMatrixGUI.setSize(10, 10);
		SokobanGame s = new SokobanGame();
		ImageMatrixGUI.getInstance().registerObserver(s);
		ImageMatrixGUI.getInstance().go();
	}
}
