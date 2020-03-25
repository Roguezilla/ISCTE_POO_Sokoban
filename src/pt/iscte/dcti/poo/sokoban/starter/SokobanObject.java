package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;

//every object in the game extends this class, but not every object can move
public abstract class SokobanObject implements ImageTile {
    void move(Direction direction) {

    }
}
