package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		ImageMatrixGUI.setSize(10, 10);
		SokobanGame.getInstance().build();
		ImageMatrixGUI.getInstance().registerObserver(SokobanGame.getInstance());
		ImageMatrixGUI.getInstance().go();
	}
}
