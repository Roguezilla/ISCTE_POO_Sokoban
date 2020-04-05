package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Main {
	public static void main(String[] args) {
		ImageMatrixGUI.setSize(10, 10);
		ImageMatrixGUI.getInstance().registerObserver(Sokoban.getInstance());
		ImageMatrixGUI.getInstance().go();
	}
}
